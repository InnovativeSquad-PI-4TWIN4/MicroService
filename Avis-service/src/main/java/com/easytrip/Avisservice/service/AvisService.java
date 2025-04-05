package com.easytrip.Avisservice.service;


import com.easytrip.Avisservice.Repository.AvisRepository;
import com.easytrip.Avisservice.models.Avis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvisService implements IAvisService {

    private final AvisRepository avisRepository;

    @Override
    public Avis createAvis(Avis avis) {
        avis.setDateAvis(LocalDateTime.now());
        avis.setApprouve(false); // par défaut, l'avis n'est pas approuvé
        return avisRepository.save(avis);
    }

    @Override
    public Avis getAvisById(Long id) {
        return avisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avis non trouvé avec l'id: " + id));
    }

    @Override
    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    @Override
    public List<Avis> getAvisByVoyageId(Long voyageId) {
        return avisRepository.findByVoyageId(voyageId);
    }

    @Override
    public List<Avis> getAvisByUtilisateurId(Long utilisateurId) {
        return avisRepository.findByUtilisateurId(utilisateurId);
    }

    @Override
    public Avis updateAvis(Long id, Avis updatedAvis) {
        Avis existingAvis = getAvisById(id);
        existingAvis.setNote(updatedAvis.getNote());
        existingAvis.setCommentaire(updatedAvis.getCommentaire());
        existingAvis.setApprouve(updatedAvis.isApprouve());
        return avisRepository.save(existingAvis);
    }

    @Override
    public void deleteAvis(Long id) {
        avisRepository.deleteById(id);
    }
}
