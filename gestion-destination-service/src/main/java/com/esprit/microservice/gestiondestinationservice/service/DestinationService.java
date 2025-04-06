package com.esprit.microservice.gestiondestinationservice.service;

import com.esprit.microservice.gestiondestinationservice.Model.Destination;
import com.esprit.microservice.gestiondestinationservice.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {
    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    public Destination saveDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    public List<Destination> getDestinationsByCountry(String country) {
        return destinationRepository.findByCountry(country);
    }

    public List<Destination> searchDestinationsByName(String name) {
        return destinationRepository.findByNameContainingIgnoreCase(name);
    }

}