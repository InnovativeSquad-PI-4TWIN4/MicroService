package tn.esprit.examen.services;

import tn.esprit.examen.entities.Agence;
import tn.esprit.examen.entities.AgenceRequestDTO;
import tn.esprit.examen.entities.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IExamenService {

    Agence addAgence(AgenceRequestDTO dto);
    List<Agence> getAllAgences();
    Agence getAgenceById(String id);
    Agence updateAgence(String id, AgenceRequestDTO dto);
    void deleteAgence(String id);
    List<Map<String, Object>> getAgencesWithResponsables();
    List<Agence> getAgencesByResponsableId(String userId);

    public Agence toggleActiveStatus(String agenceId, boolean active);
    long getNombreAgencesParRole(String role);
    List<Agence> searchAgences(String nom, String adresse, Boolean active);
    List<Map<String, Object>> getTopResponsables(int limit);
    String exportAgencesWithResponsablesCSV()  throws IOException ;
    public Map<String, Map<String, Long>> getStatistiquesParVilleEtStatut();

    Map<String, Double> getTauxDisponibiliteParVille();
    public Map<String, Object> comparerResponsables(String userId1, String userId2);
    public byte[] generateActiveAgencesPdf() throws IOException;

    public void envoyerAgencesParEmail(String destinataire) throws IOException, MessagingException;

}
