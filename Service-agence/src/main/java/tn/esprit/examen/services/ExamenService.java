package tn.esprit.examen.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.examen.entities.Agence;
import tn.esprit.examen.entities.AgenceRequestDTO;
import tn.esprit.examen.entities.User;
import tn.esprit.examen.repositories.AgenceRepository;
import tn.esprit.examen.repositories.UserRepository;

import com.itextpdf.layout.Document;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamenService implements IExamenService {

    private final AgenceRepository agenceRepository;
    private final UserRepository userRepository;

    @Override
    public Agence addAgence(AgenceRequestDTO dto) {
        validateUserExists(dto.getResponsableId());

        Agence agence = new Agence();
        setFields(dto, agence);
        return agenceRepository.save(agence);
    }

    @Override
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence getAgenceById(String id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée avec id: " + id));
    }

    @Override
    public Agence updateAgence(String id, AgenceRequestDTO dto) {
        Agence agence = getAgenceById(id);
        validateUserExists(dto.getResponsableId());
        setFields(dto, agence);
        return agenceRepository.save(agence);
    }

    @Override
    public void deleteAgence(String id) {
        if (!agenceRepository.existsById(id)) {
            throw new RuntimeException("Agence non trouvée avec id: " + id);
        }
        agenceRepository.deleteById(id);
    }

    private void validateUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User non trouvé avec id: " + userId);
        }
    }

    private void setFields(AgenceRequestDTO dto, Agence agence) {
        agence.setNomAg(dto.getNomAg());
        agence.setAdresse(dto.getAdresse());
        agence.setEmail(dto.getEmail());
        agence.setTelephone(dto.getTelephone());
        agence.setSiteWeb(dto.getSiteWeb());
        agence.setDescription(dto.getDescription());
        agence.setActive(dto.isActive());
        agence.setResponsableId(dto.getResponsableId());
    }
    @Override
    public List<Map<String, Object>> getAgencesWithResponsables() {
        List<Agence> agences = agenceRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Agence agence : agences) {
            Map<String, Object> map = new HashMap<>();
            map.put("agence", agence);
            userRepository.findById(agence.getResponsableId())
                    .ifPresent(user -> map.put("responsable", user));
            response.add(map);
        }

        return response;
    }
    @Override
    public List<Agence> getAgencesByResponsableId(String userId) {
        return agenceRepository.findAll()
                .stream()
                .filter(agence -> userId.equals(agence.getResponsableId()))
                .collect(Collectors.toList());
    }
    @Override
    public Agence toggleActiveStatus(String agenceId, boolean active) {
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));

        agence.setActive(active);
        return agenceRepository.save(agence);
    }

    @Override
    public long getNombreAgencesParRole(String role) {
        List<Agence> agences = agenceRepository.findAll();
        long count = 0;

        for (Agence agence : agences) {
            String userId = agence.getResponsableId();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent() && optionalUser.get().getRole().equalsIgnoreCase(role)) {
                count++;
            }
        }

        return count;
    }
    @Override
    public List<Agence> searchAgences(String nom, String adresse, Boolean active) {
        return agenceRepository.findAll()
                .stream()
                .filter(a -> (nom == null || a.getNomAg().toLowerCase().contains(nom.toLowerCase())))
                .filter(a -> (adresse == null || a.getAdresse().toLowerCase().contains(adresse.toLowerCase())))
                .filter(a -> (active == null || a.isActive() == active))
                .collect(Collectors.toList());
    }
    @Override
    public List<Map<String, Object>> getTopResponsables(int limit) {
        List<Agence> agences = agenceRepository.findAll();

        Map<String, Long> countMap = agences.stream()
                .collect(Collectors.groupingBy(Agence::getResponsableId, Collectors.counting()));

        return countMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    userRepository.findById(entry.getKey()).ifPresent(user -> {
                        result.put("user", user);
                        result.put("nombreAgences", entry.getValue());
                    });
                    return result;
                })
                .collect(Collectors.toList());
    }
    @Override
    public String exportAgencesWithResponsablesCSV()  throws IOException {
        List<Agence> agences = agenceRepository.findAll();

        StringWriter writer = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Nom Agence", "Adresse", "Email Agence", "Responsable Nom", "Responsable Email", "Rôle"));

        for (Agence agence : agences) {
            Optional<User> userOpt = userRepository.findById(agence.getResponsableId());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                csvPrinter.printRecord(
                        agence.getNomAg(),
                        agence.getAdresse(),
                        agence.getEmail(),
                        user.getFirstname() + " " + user.getLastname(),
                        user.getEmail(),
                        user.getRole()
                );
            }
        }

        csvPrinter.flush();
        return writer.toString(); // le contenu CSV prêt à envoyer
    }
    @Override
    public Map<String, Map<String, Long>> getStatistiquesParVilleEtStatut() {
        List<Agence> agences = agenceRepository.findAll();

        Map<String, Map<String, Long>> result = new HashMap<>();

        for (Agence agence : agences) {
            String ville = agence.getAdresse(); // ou un champ spécifique si tu l’as
            boolean active = agence.isActive();

            result.putIfAbsent(ville, new HashMap<>());
            Map<String, Long> statsVille = result.get(ville);

            if (active) {
                statsVille.put("actives", statsVille.getOrDefault("actives", 0L) + 1);
            } else {
                statsVille.put("inactives", statsVille.getOrDefault("inactives", 0L) + 1);
            }
        }

        return result;
    }
    @Override
    public Map<String, Double> getTauxDisponibiliteParVille() {
        List<Agence> agences = agenceRepository.findAll();
        Map<String, List<Agence>> agencesParVille = agences.stream()
                .collect(Collectors.groupingBy(Agence::getAdresse));

        Map<String, Double> tauxParVille = new HashMap<>();

        for (Map.Entry<String, List<Agence>> entry : agencesParVille.entrySet()) {
            String ville = entry.getKey();
            List<Agence> agencesDansVille = entry.getValue();

            long total = agencesDansVille.size();
            long actives = agencesDansVille.stream().filter(Agence::isActive).count();

            double taux = (actives * 100.0) / total;
            tauxParVille.put(ville, taux);
        }

        return tauxParVille;
    }
    @Override
    public Map<String, Object> comparerResponsables(String userId1, String userId2) {
        Map<String, Object> result = new HashMap<>();

        List<Agence> agences1 = agenceRepository.findAll().stream()
                .filter(a -> userId1.equals(a.getResponsableId())).toList();
        List<Agence> agences2 = agenceRepository.findAll().stream()
                .filter(a -> userId2.equals(a.getResponsableId())).toList();

        long total1 = agences1.size();
        long total2 = agences2.size();
        long actives1 = agences1.stream().filter(Agence::isActive).count();
        long actives2 = agences2.stream().filter(Agence::isActive).count();

        double taux1 = total1 == 0 ? 0.0 : (actives1 * 100.0) / total1;
        double taux2 = total2 == 0 ? 0.0 : (actives2 * 100.0) / total2;

        result.put("user1", Map.of(
                "userId", userId1,
                "nombreAgences", total1,
                "tauxDisponibilite", taux1
        ));
        result.put("user2", Map.of(
                "userId", userId2,
                "nombreAgences", total2,
                "tauxDisponibilite", taux2
        ));

        return result;
    }
@Override

public byte[] generateActiveAgencesPdf() throws IOException {
    List<Agence> agences = agenceRepository.findAll().stream()
            .filter(Agence::isActive)
            .toList();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(out);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    document.add(new Paragraph("Liste des agences actives").setBold().setFontSize(18).setMarginBottom(10));

    // Créer un tableau avec 4 colonnes de largeur automatique
    Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3, 4}));
    table.setWidth(UnitValue.createPercentValue(100));

    // En-têtes
    table.addHeaderCell(new Cell().add(new Paragraph("Nom agence").setBold()));
    table.addHeaderCell(new Cell().add(new Paragraph("Adresse").setBold()));
    table.addHeaderCell(new Cell().add(new Paragraph("Responsable").setBold()));
    table.addHeaderCell(new Cell().add(new Paragraph("Email responsable").setBold()));

    // Remplissage des lignes
    for (Agence agence : agences) {
        User user = userRepository.findById(agence.getResponsableId()).orElse(null);
        table.addCell(agence.getNomAg());
        table.addCell(agence.getAdresse());
        table.addCell(user != null ? user.getFirstname() + " " + user.getLastname() : "N/A");
        table.addCell(user != null ? user.getEmail() : "N/A");
    }

    document.add(table);
    document.close();

    return out.toByteArray();
}
    @Autowired
    private JavaMailSender mailSender;
@Override
    public void envoyerAgencesParEmail(String destinataire) throws IOException, MessagingException {
        List<Agence> agences = agenceRepository.findAll();

        StringWriter writer = new StringWriter();
        CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Agence", "Adresse", "Responsable"));

        for (Agence agence : agences) {
            User u = userRepository.findById(agence.getResponsableId()).orElse(null);
            csv.printRecord(agence.getNomAg(), agence.getAdresse(),
                    u != null ? u.getFirstname() + " " + u.getLastname() : "N/A");
        }

        csv.flush();
        byte[] attachmentData = writer.toString().getBytes(StandardCharsets.UTF_8);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destinataire);
        helper.setSubject("Liste des agences");
        helper.setText("Veuillez trouver en pièce jointe la liste des agences.", true);
        helper.addAttachment("agences.csv", new ByteArrayResource(attachmentData));

        mailSender.send(message);
    }


}
