package com.easytrip.Avisservice.controller;
import com.easytrip.Avisservice.models.Avis;
import com.easytrip.Avisservice.service.AvisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // à adapter si tu veux restreindre l'accès CORS
public class AvissController {

    private final AvisService avisService;

    // ➕ Ajouter un avis
    @PostMapping("/addAvis")
    public ResponseEntity<Avis> createAvis(@RequestBody Avis avis) {
        return ResponseEntity.ok(avisService.createAvis(avis));
    }

    // 🔍 Récupérer un avis par son ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<Avis> getAvisById(@PathVariable Long id) {
        return ResponseEntity.ok(avisService.getAvisById(id));
    }

    // 📃 Récupérer tous les avis
    @GetMapping("/GetallAvis")
    public ResponseEntity<List<Avis>> getAllAvis() {
        return ResponseEntity.ok(avisService.getAllAvis());
    }

    // 📌 Récupérer les avis liés à un voyage spécifique
    @GetMapping("/voyage/{voyageId}")
    public ResponseEntity<List<Avis>> getAvisByVoyageId(@PathVariable Long voyageId) {
        return ResponseEntity.ok(avisService.getAvisByVoyageId(voyageId));
    }

    // 🙋‍♂️ Récupérer les avis d’un utilisateur
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<Avis>> getAvisByUtilisateurId(@PathVariable Long utilisateurId) {
        return ResponseEntity.ok(avisService.getAvisByUtilisateurId(utilisateurId));
    }

    // ✏️ Modifier un avis
    @PutMapping("/updateAvis/{id}")
    public ResponseEntity<Avis> updateAvis(@PathVariable Long id, @RequestBody Avis updatedAvis) {
        return ResponseEntity.ok(avisService.updateAvis(id, updatedAvis));
    }

    // ❌ Supprimer un avis
    @DeleteMapping("/deleteAvis/{id}")
    public ResponseEntity<Void> deleteAvis(@PathVariable Long id) {
        avisService.deleteAvis(id);
        return ResponseEntity.noContent().build();
    }
}
