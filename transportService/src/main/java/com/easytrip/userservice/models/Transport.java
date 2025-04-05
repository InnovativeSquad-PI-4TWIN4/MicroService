package com.easytrip.userservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transports")
public class Transport {

    @Id
    private String id;

    private String type; // Ex : "Avion", "Bus", "Train", etc.

    private String compagnie; // Nom de la compagnie : Ex : "Air France", "SNCF", etc.

    private int capacite; // Nombre de places

    private String numero; // Numéro du transport (ex: numéro de vol)

    private String villeDepart;

    private String villeArrivee;

    private LocalDateTime dateDepart;

    private LocalDateTime dateArrivee;

    private double prix;

    // Au lieu de relation, on stocke juste l'id du voyage
    private String voyageId;
}
