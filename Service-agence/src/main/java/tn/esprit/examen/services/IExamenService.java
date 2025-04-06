package tn.esprit.examen.services;

import tn.esprit.examen.entities.Agence;

import java.util.List;

public interface IExamenService {

    Agence ajouterAgence(Agence agence);
    Agence modifierAgence(Long id, Agence agence);
    void supprimerAgence(Long id);
    Agence getAgenceById(Long id);
    List<Agence> getAllAgences();

}
