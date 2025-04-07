package com.esprit.microservice.gestiondestinationservice.service;

import com.esprit.microservice.gestiondestinationservice.Model.Destination;
import com.esprit.microservice.gestiondestinationservice.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    // Changed from private to protected to allow transactional proxy
    @Transactional
    protected Destination initializeCollections(Destination destination) {
        if (destination.getTags() != null) destination.getTags().size();
        if (destination.getLandmarks() != null) destination.getLandmarks().size();
        if (destination.getLocalCuisine() != null) destination.getLocalCuisine().size();
        return destination;
    }

    // User-specific operations
    @Transactional(readOnly = true)
    public List<Destination> getUserDestinations(Long userId) {
        return destinationRepository.findByUserId(userId).stream()
                .map(this::initializeCollections)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Destination> getUserDestinationsPaginated(Long userId, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return destinationRepository.findByUserId(userId, pageable)
                .map(this::initializeCollections);
    }

    @Transactional
    public Destination createUserDestination(Destination destination, Long userId) {
        destination.setUserId(userId);
        return destinationRepository.save(destination);
    }

    @Transactional
    public Destination updateUserDestination(Long id, Destination destination, Long userId) {
        Destination existing = destinationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Destination not found with id: %d for user: %d", id, userId)
                ));

        existing.setName(destination.getName());
        existing.setCountry(destination.getCountry());
        existing.setDescription(destination.getDescription());
        existing.setAveragePrice(destination.getAveragePrice());
        existing.setImageUrl(destination.getImageUrl());
        existing.setRating(destination.getRating());
        existing.setTags(destination.getTags());
        existing.setCurrency(destination.getCurrency());
        existing.setTimezone(destination.getTimezone());
        existing.setBestTimeToVisit(destination.getBestTimeToVisit());
        existing.setLandmarks(destination.getLandmarks());
        existing.setLocalCuisine(destination.getLocalCuisine());
        existing.setSafetyIndex(destination.getSafetyIndex());
        existing.setIsFeatured(destination.getIsFeatured());

        return destinationRepository.save(existing);
    }

    @Transactional
    public void deleteUserDestination(Long id, Long userId) {
        Destination destination = destinationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Destination not found with id: %d for user: %d", id, userId)
                ));
        destinationRepository.delete(destination);
    }

    // Public operations
    @Transactional(readOnly = true)
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(this::initializeCollections)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Destination> getDestinationsPaginated(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return destinationRepository.findAll(pageable)
                .map(this::initializeCollections);
    }

    @Transactional(readOnly = true)
    public Destination getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Destination not found with id: %d", id)
                ));
        return initializeCollections(destination);
    }

    // Search and filter operations
    @Transactional(readOnly = true)
    public List<Destination> searchDestinations(String query) {
        return destinationRepository.findByNameContainingIgnoreCase(query).stream()
                .map(this::initializeCollections)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Destination> filterDestinationsByCountry(String country) {
        return destinationRepository.findByCountryIgnoreCase(country).stream()
                .map(this::initializeCollections)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Destination> filterDestinationsByPriceRange(Double minPrice, Double maxPrice) {
        return destinationRepository.findByPriceRange(minPrice, maxPrice).stream()
                .map(this::initializeCollections)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Destination> filterDestinationsByTag(String tag) {
        return destinationRepository.findByTag(tag).stream()
                .map(this::initializeCollections)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Destination> getFeaturedDestinations() {
        return destinationRepository.findByIsFeaturedTrue().stream()
                .map(this::initializeCollections)
                .collect(Collectors.toList());
    }

    // Analytics operations
    @Transactional(readOnly = true)
    public Map<String, Long> getDestinationCountByCountry() {
        return destinationRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Destination::getCountry,
                        Collectors.counting()
                ));
    }

    @Transactional(readOnly = true)
    public Map<String, Double> getAveragePricesByCountry() {
        return destinationRepository.findAllUniqueCountries().stream()
                .collect(Collectors.toMap(
                        country -> country,
                        country -> destinationRepository.getAveragePriceByCountry(country)
                ));
    }

    // Recommendation system
    @Transactional(readOnly = true)
    public List<Destination> getRecommendations(Long destinationId, int limit) {
        Destination target = getDestinationById(destinationId);
        return getAllDestinations().stream()
                .filter(d -> !d.getId().equals(destinationId))
                .sorted(Comparator.comparingDouble(d -> calculateSimilarityScore(target, d)))
                .limit(limit)
                .collect(Collectors.toList());
    }

    // Non-transactional private method - no annotation needed
    private double calculateSimilarityScore(Destination target, Destination candidate) {
        double score = 0;

        // Country match
        if (target.getCountry().equalsIgnoreCase(candidate.getCountry())) {
            score += 5;
        }

        // Price similarity
        double priceDiff = Math.abs(target.getAveragePrice() - candidate.getAveragePrice());
        if (priceDiff < 100) score += 5;
        else if (priceDiff < 300) score += 3;
        else if (priceDiff < 500) score += 1;

        // Tag matches
        if (target.getTags() != null && candidate.getTags() != null) {
            Set<String> commonTags = new HashSet<>(target.getTags());
            commonTags.retainAll(candidate.getTags());
            score += commonTags.size() * 3;
        }

        return score;
    }

    @Transactional
    public void incrementPopularity(Long id) {
        Destination destination = getDestinationById(id);
        destination.setPopularityScore(destination.getPopularityScore() + 1);
        destinationRepository.save(destination);
    }
}