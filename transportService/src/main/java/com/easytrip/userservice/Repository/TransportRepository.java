package com.easytrip.userservice.Repository;

import com.easytrip.userservice.models.Transport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends MongoRepository<Transport, String> {
    // Tu peux aussi ajouter des méthodes personnalisées ici
}
