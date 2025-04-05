package com.easytrip.userservice.service;

import com.easytrip.userservice.models.Reservation;

import java.util.List;
import java.util.Map;

public interface IReservationService {

    List<Reservation> getAllReservations();

    Reservation getReservationById(Long id);

    Reservation createReservation(Reservation reservation);

    Reservation updateReservation(Long id, Reservation updated);

    void deleteReservation(Long id);

    // Nouvelle méthode pour récupérer la réservation + infos utilisateur
    Map<String, Object> getReservationWithUser(Long id);
}
