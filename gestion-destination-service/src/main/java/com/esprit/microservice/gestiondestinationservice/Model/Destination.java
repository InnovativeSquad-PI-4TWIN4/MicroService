package com.esprit.microservice.gestiondestinationservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Country is mandatory")
    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
    @Column(nullable = false, length = 50)
    private String country;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    @Column(length = 2000)
    private String description;

    @NotNull(message = "Average price is mandatory")
    @PositiveOrZero(message = "Average price must be positive or zero")
    @Column(nullable = false)
    private Double averagePrice;

    @URL(message = "Image URL must be valid")
    private String imageUrl;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
    private Double rating = 0.0;

    @Min(value = 0, message = "Popularity score cannot be negative")
    private Integer popularityScore = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destination_tags", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "tag")
    @Fetch(FetchMode.SUBSELECT)
    private Set<String> tags = new HashSet<>();

    @Size(max = 3, message = "Currency code must be 3 characters")
    private String currency;

    private String timezone;

    private String bestTimeToVisit;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destination_landmarks", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "landmark")
    @Fetch(FetchMode.SUBSELECT)
    private Set<String> landmarks = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destination_cuisines", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "cuisine")
    @Fetch(FetchMode.SUBSELECT)
    private Set<String> localCuisine = new HashSet<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Min(value = 0, message = "Safety index must be at least 0")
    @Max(value = 100, message = "Safety index must be at most 100")
    private Integer safetyIndex = 50;

    private Boolean isFeatured = false;

    @NotNull(message = "User ID is mandatory")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}