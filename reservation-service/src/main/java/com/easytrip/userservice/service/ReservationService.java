package com.easytrip.userservice.service;

import com.easytrip.userservice.Repository.ReservationRepository;
import com.easytrip.userservice.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation createReservation(Reservation reservation) {
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
}

