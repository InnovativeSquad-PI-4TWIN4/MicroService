package com.esprit.microservice.gestiondestinationservice.repository;


import com.esprit.microservice.gestiondestinationservice.Model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCountry(String country);
    List<Destination> findByNameContainingIgnoreCase(String name);
}