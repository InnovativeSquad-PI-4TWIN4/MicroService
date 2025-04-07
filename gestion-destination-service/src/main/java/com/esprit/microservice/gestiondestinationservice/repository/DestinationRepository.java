package com.esprit.microservice.gestiondestinationservice.repository;

import com.esprit.microservice.gestiondestinationservice.Model.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    List<Destination> findByUserId(Long userId);

    Page<Destination> findByUserId(Long userId, Pageable pageable);

    Optional<Destination> findByIdAndUserId(Long id, Long userId);

    List<Destination> findByCountryIgnoreCase(String country);

    List<Destination> findByNameContainingIgnoreCase(String name);

    @Query("SELECT d FROM Destination d WHERE d.averagePrice BETWEEN :minPrice AND :maxPrice")
    List<Destination> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT d FROM Destination d WHERE :tag MEMBER OF d.tags")
    List<Destination> findByTag(@Param("tag") String tag);

    List<Destination> findByRatingGreaterThanEqual(Double minRating);

    List<Destination> findByIsFeaturedTrue();

    List<Destination> findBySafetyIndexGreaterThanEqual(Integer safetyThreshold);

    @Query("SELECT d FROM Destination d ORDER BY d.popularityScore DESC")
    Page<Destination> findTopPopularDestinations(Pageable pageable);

    @Query("SELECT d FROM Destination d WHERE LOWER(d.bestTimeToVisit) LIKE LOWER(CONCAT('%', :season, '%'))")
    List<Destination> findByBestTimeToVisit(@Param("season") String season);

    @Query("SELECT DISTINCT d.country FROM Destination d ORDER BY d.country")
    List<String> findAllUniqueCountries();

    @Query("SELECT AVG(d.averagePrice) FROM Destination d WHERE d.country = :country")
    Double getAveragePriceByCountry(@Param("country") String country);

    @Query("SELECT d FROM Destination d WHERE d.userId = :userId AND :tag MEMBER OF d.tags")
    List<Destination> findByUserIdAndTag(@Param("userId") Long userId, @Param("tag") String tag);
}