package tn.esprit.examen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.examen.entities.Agence;
import tn.esprit.examen.repositories.AgenceRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ExamenService implements IExamenService{


    AgenceRepository agenceRepository;


    @Scheduled(fixedDelay = 60000)
    void scheduler() {
        log.info("test");
    }


    @Override
    public Agence ajouterAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public Agence modifierAgence(Long id, Agence agence) {
        Agence existingAgence = agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));

        existingAgence.setNomAg(agence.getNomAg());
        existingAgence.setAdresse(agence.getAdresse());
        existingAgence.setEmail(agence.getEmail());
        existingAgence.setTelephone(agence.getTelephone());
        existingAgence.setSiteWeb(agence.getSiteWeb());
        existingAgence.setDescription(agence.getDescription());
        existingAgence.setActive(agence.isActive());

        // ✅ mettre à jour le responsable si fourni
        if (agence.getResponsableId() != null) {
            existingAgence.setResponsableId(agence.getResponsableId());
        }

        return agenceRepository.save(existingAgence);
    }

    @Override
    public void supprimerAgence(Long id) {
        agenceRepository.deleteById(id);
    }

    @Override
    public Agence getAgenceById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));
    }

    @Override
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }



}
