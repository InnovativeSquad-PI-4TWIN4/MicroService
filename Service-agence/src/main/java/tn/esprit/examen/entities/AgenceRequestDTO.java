package tn.esprit.examen.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AgenceRequestDTO {
        private String nomAg;
        private String adresse;
        private String email;
        private String telephone;
        private String siteWeb;
        private String description;
        private boolean active;
        private String responsableId; // ID du user à affecter
    }

