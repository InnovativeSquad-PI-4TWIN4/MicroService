package com.easytrip.userservice.service;

import com.easytrip.userservice.Repository.TransportRepository;
import com.easytrip.userservice.models.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService implements ITransportService {

    @Autowired
    private TransportRepository transportRepository;

    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    @Override
    public Transport getTransportById(String id) {
        return transportRepository.findById(id).orElse(null);
    }

    @Override
    public Transport createTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public Transport updateTransport(String id, Transport updated) {
        Transport existing = transportRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setType(updated.getType());
            existing.setCompagnie(updated.getCompagnie());
            existing.setCapacite(updated.getCapacite());
            existing.setNumero(updated.getNumero());
            existing.setVilleDepart(updated.getVilleDepart());
            existing.setVilleArrivee(updated.getVilleArrivee());
            existing.setDateDepart(updated.getDateDepart());
            existing.setDateArrivee(updated.getDateArrivee());
            existing.setPrix(updated.getPrix());
            existing.setVoyageId(updated.getVoyageId());
            existing.setUserId(updated.getUserId()); // 🆕 Ajout du lien vers utilisateur
            return transportRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteTransport(String id) {
        transportRepository.deleteById(id);
    }

    @Override
    public List<Transport> getTransportsByUserId(Long userId) {
        return transportRepository.findByUserId(userId);
    }
}
