package tn.esprit.examen.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.entities.Agence;
import tn.esprit.examen.entities.AgenceRequestDTO;
import tn.esprit.examen.entities.User;
import tn.esprit.examen.services.IExamenService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/agences")
public class ExamenController {

    IExamenService examenService;

    // http://localhost:8089/examen/projet/add-etudiant


    @PostMapping("/add-agence")
    public ResponseEntity<Agence> addAgence(@RequestBody AgenceRequestDTO dto) {
        return ResponseEntity.ok(examenService.addAgence(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Agence>> getAllAgences() {
        return ResponseEntity.ok(examenService.getAllAgences());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable String id) {
        return ResponseEntity.ok(examenService.getAgenceById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Agence> updateAgence(@PathVariable String id, @RequestBody AgenceRequestDTO dto) {
        return ResponseEntity.ok(examenService.updateAgence(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable String id) {
        examenService.deleteAgence(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/Lister agences avec User responsable")    //Lister agences avec User responsable
    public ResponseEntity<List<Map<String, Object>>> getAgencesWithResponsables() {
        return ResponseEntity.ok(examenService.getAgencesWithResponsables());
    }
    @GetMapping("/Trouver agences par user/{userId}")  //Trouver agences par user
    public ResponseEntity<List<Agence>> getAgencesByResponsable(@PathVariable String userId) {
        return ResponseEntity.ok(examenService.getAgencesByResponsableId(userId));
    }

    @PutMapping("/{id}/active_desactiver")   //activer/désactiver
    public ResponseEntity<Agence> updateAgenceStatus(@PathVariable String id, @RequestParam boolean active) {
        return ResponseEntity.ok(examenService.toggleActiveStatus(id, active));
    }

    @GetMapping("/stats/Nombre d’agences par rôle")      //Nombre d’agences par rôle
    public ResponseEntity<Long> getNombreAgencesParRole(@RequestParam String role) {
        return ResponseEntity.ok(examenService.getNombreAgencesParRole(role));
    }

    @GetMapping("/search_par_nom_adresse_status")       //Recherche dynamique multi-critères
    public ResponseEntity<List<Agence>> searchAgences(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String adresse,
            @RequestParam(required = false) Boolean active
    ) {
        return ResponseEntity.ok(examenService.searchAgences(nom, adresse, active));
    }

    @GetMapping("/top-N-responsables")    //Top N responsables
    public ResponseEntity<List<Map<String, Object>>> getTopResponsables(
            @RequestParam(defaultValue = "3") int limit
    ) {
        return ResponseEntity.ok(examenService.getTopResponsables(limit));
    }

    @GetMapping("/export_agence_with_responsable/csv")
    public ResponseEntity<byte[]> exportCSV()  throws IOException {
        String csvData = examenService.exportAgencesWithResponsablesCSV();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=agences.csv")
                .header("Content-Type", "text/csv")
                .body(csvData.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/stats/actives_vs_inactives_par_ville")   //Analyse des agences actives vs inactives par ville
    public ResponseEntity<Map<String, Map<String, Long>>> getStatsParVille() {
        return ResponseEntity.ok(examenService.getStatistiquesParVilleEtStatut());
    }

    @GetMapping("/stats/disponibilite-par-ville")   //getTauxDisponibiliteParVille
    public ResponseEntity<Map<String, Double>> getTauxParVille() {
        return ResponseEntity.ok(examenService.getTauxDisponibiliteParVille());
    }
    @GetMapping("/compare")   //Comparer deux responsables par performance
    public ResponseEntity<Map<String, Object>> comparerResponsables(
            @RequestParam String user1,
            @RequestParam String user2) {
        return ResponseEntity.ok(examenService.comparerResponsables(user1, user2));
    }

    @GetMapping("/export/pdf/actives")    //Générer un PDF des agences actives avec leurs responsables
    public ResponseEntity<byte[]> exportActivesAsPdf() throws IOException {
        byte[] pdfData = examenService.generateActiveAgencesPdf();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=agences-actives.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdfData);
    }
    @PostMapping("/mail/send")
    public ResponseEntity<String> sendAgencesByEmail(@RequestParam String to) throws IOException, MessagingException {
        examenService.envoyerAgencesParEmail(to);
        return ResponseEntity.ok("Email envoyé à " + to);
    }


}
