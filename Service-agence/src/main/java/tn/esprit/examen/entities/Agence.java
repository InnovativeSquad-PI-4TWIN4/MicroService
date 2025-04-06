package tn.esprit.examen.entities;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "agences")
public class Agence implements Serializable {
    @Id

    private Long idAgence; // Clé primaire

    private String nomAg; // Nom de l'agence

    private String adresse; // Adresse physique de l'agence

    private String email; // Email de contact

    private String telephone; // Numéro de téléphone

    private String siteWeb; // Lien vers le site web (optionnel)

    private String description; // Brève description de l'agence

    private boolean active; // Statut de l'agence (active ou non)

   // @ManyToOne
    // private User responsable; // Lien vers un user représentant ou gérant l’agence
}