package tn.esprit.examen.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.entities.Agence;
import tn.esprit.examen.services.IExamenService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/agence")
public class ExamenController {

    IExamenService examenService;

    // http://localhost:8089/examen/projet/add-etudiant

    @PostMapping("/add-agence")
    public Agence ajouterAgence(@RequestBody Agence agence) {
        return examenService.ajouterAgence(agence);
    }

    @PutMapping("/{id}")
    public Agence modifierAgence(@PathVariable Long id, @RequestBody Agence agence) {
        return examenService.modifierAgence(id, agence);
    }

    @DeleteMapping("/{id}")
    public void supprimerAgence(@PathVariable Long id) {
        examenService.supprimerAgence(id);
    }

    @GetMapping("/{id}")
    public Agence getAgence(@PathVariable Long id) {
        return examenService.getAgenceById(id);
    }

    @GetMapping
    public List<Agence> getAllAgences() {
        return examenService.getAllAgences();
    }



}
