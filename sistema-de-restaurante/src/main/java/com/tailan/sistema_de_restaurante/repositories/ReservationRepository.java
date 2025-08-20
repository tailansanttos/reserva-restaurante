package com.tailan.sistema_de_restaurante.repositories;

import com.tailan.sistema_de_restaurante.model.reserva.Reservation;
import com.tailan.sistema_de_restaurante.model.reserva.StatusReserva;
import com.tailan.sistema_de_restaurante.model.usuario.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByMesaIdAndReservationDate(UUID mesaId, LocalDateTime reservationDate);

    boolean existsByMesaIdAndReservationDateAndStatus(UUID mesaId, LocalDateTime reservationDate, StatusReserva status);

    List<Reservation> findAllByUser(User user);

    Optional<Reservation> findByUserAndId(User user, UUID id);
}
