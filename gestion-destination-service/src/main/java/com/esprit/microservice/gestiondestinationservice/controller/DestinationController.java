package com.esprit.microservice.gestiondestinationservice.controller;

import com.esprit.microservice.gestiondestinationservice.Model.Destination;
import com.esprit.microservice.gestiondestinationservice.service.DestinationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
@Tag(name = "Destination Management", description = "APIs for managing travel destinations")
public class DestinationController {

    private final DestinationService destinationService;

    // Public endpoints
    @GetMapping("/public")  // This matches the security config
    public ResponseEntity<List<Destination>> getAllPublicDestinations() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }
    @GetMapping("/test-token")
    @Operation(summary = "Get a test token (for development only)")
    public String getTestToken() {
        return Jwts.builder()
                .setSubject("123") // Mock user ID
                .claim("authorities", List.of("ROLE_USER"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(Keys.hmacShaKeyFor("your-very-secure-secret-key-change-this-in-production".getBytes()))
                .compact();
    }


    @GetMapping("/public/{id}")
    @Operation(summary = "Get a public destination by ID")
    public ResponseEntity<Destination> getPublicDestinationById(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.getDestinationById(id));
    }

    @GetMapping("/public/search")
    @Operation(summary = "Search destinations by name")
    public ResponseEntity<List<Destination>> searchDestinations(@RequestParam String query) {
        return ResponseEntity.ok(destinationService.searchDestinations(query));
    }

    @GetMapping("/public/country/{country}")
    @Operation(summary = "Filter destinations by country")
    public ResponseEntity<List<Destination>> getDestinationsByCountry(@PathVariable String country) {
        return ResponseEntity.ok(destinationService.filterDestinationsByCountry(country));
    }

    // User-specific endpoints
    @GetMapping("/user")
    @Operation(summary = "Get all destinations for current user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Destination>> getUserDestinations(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(destinationService.getUserDestinations(userId));
    }

    @PostMapping("/user")
    @Operation(summary = "Create a new destination for current user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Destination> createUserDestination(
            @RequestBody Destination destination,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(destinationService.createUserDestination(destination, userId));
    }

    @PutMapping("/user/{id}")
    @Operation(summary = "Update a destination for current user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Destination> updateUserDestination(
            @PathVariable Long id,
            @RequestBody Destination destination,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(destinationService.updateUserDestination(id, destination, userId));
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Delete a destination for current user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteUserDestination(
            @PathVariable Long id,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        destinationService.deleteUserDestination(id, userId);
        return ResponseEntity.noContent().build();
    }

    // Analytics endpoints
    @GetMapping("/analytics/count-by-country")
    @Operation(summary = "Get destination count by country")
    public ResponseEntity<Map<String, Long>> getDestinationCountByCountry() {
        return ResponseEntity.ok(destinationService.getDestinationCountByCountry());
    }

    @GetMapping("/analytics/avg-price-by-country")
    @Operation(summary = "Get average price by country")
    public ResponseEntity<Map<String, Double>> getAveragePricesByCountry() {
        return ResponseEntity.ok(destinationService.getAveragePricesByCountry());
    }

    // Recommendation system
    @GetMapping("/{id}/recommendations")
    @Operation(summary = "Get recommended destinations")
    public ResponseEntity<List<Destination>> getRecommendations(
            @PathVariable Long id,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(destinationService.getRecommendations(id, limit));
    }

    // Popularity tracking
    @PostMapping("/{id}/track-view")
    @Operation(summary = "Track a view for popularity")
    public ResponseEntity<Void> trackDestinationView(@PathVariable Long id) {
        destinationService.incrementPopularity(id);
        return ResponseEntity.ok().build();
    }
}