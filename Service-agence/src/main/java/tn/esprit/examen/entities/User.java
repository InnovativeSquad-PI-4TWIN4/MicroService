package tn.esprit.examen.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") // nom de la collection Mongo
public class User {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
}
