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

    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    public Transport getTransportById(String id) {
        return transportRepository.findById(id).orElse(null);
    }

    public Transport createTransport(Transport transport) {
        return transportRepository.save(transport);
    }

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
            return transportRepository.save(existing);
        }
        return null;
    }

    public void deleteTransport(String id) {
        transportRepository.deleteById(id);
    }
}
