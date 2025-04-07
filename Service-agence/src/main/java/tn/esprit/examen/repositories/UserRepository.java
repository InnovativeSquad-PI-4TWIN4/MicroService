package tn.esprit.examen.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.examen.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
}