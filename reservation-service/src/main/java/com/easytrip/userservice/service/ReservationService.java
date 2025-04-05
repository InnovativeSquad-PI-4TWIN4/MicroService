package com.easytrip.userservice.service;

import com.easytrip.userservice.Repository.ReservationRepository;
import com.easytrip.userservice.UserClient.UserClient;
import com.easytrip.userservice.dto.UserResponse;
import com.easytrip.userservice.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final UserClient userClient;

    public ReservationService(ReservationRepository reservationRepository, UserClient userClient) {
        this.reservationRepository = reservationRepository;
        this.userClient = userClient;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation createReservation(Reservation reservation) {
        // Appel au microservice user-service
        UserResponse user = userClient.getUserById(reservation.getUserId());
        System.out.println("Réservation pour : " + user.getFirstname() + " " + user.getLastname());

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation updated) {
        Reservation r = reservationRepository.findById(id).orElse(null);
        if (r != null) {
            r.setDestination(updated.getDestination());
            r.setDateDepart(updated.getDateDepart());
            r.setDateRetour(updated.getDateRetour());
            r.setNombrePersonnes(updated.getNombrePersonnes());
            return reservationRepository.save(r);
        }
        return null;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
    public Map<String, Object> getReservationWithUser(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) return null;

        UserResponse user = userClient.getUserById(reservation.getUserId());

        Map<String, Object> result = new HashMap<>();
        result.put("reservation", reservation);
        result.put("user", user);
        return result;
    }


}
