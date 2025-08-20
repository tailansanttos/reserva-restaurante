package com.tailan.sistema_de_restaurante.service.impl;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaRequestDto;
import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaRequestDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserResponseDto;
import com.tailan.sistema_de_restaurante.mapper.ReservaMapper;
import com.tailan.sistema_de_restaurante.model.mesa.Mesa;
import com.tailan.sistema_de_restaurante.model.reserva.Reservation;
import com.tailan.sistema_de_restaurante.model.reserva.StatusReserva;
import com.tailan.sistema_de_restaurante.model.usuario.User;
import com.tailan.sistema_de_restaurante.repositories.ReservationRepository;
import com.tailan.sistema_de_restaurante.service.MesaService;
import com.tailan.sistema_de_restaurante.service.ReservaService;
import com.tailan.sistema_de_restaurante.service.UserService;
import com.tailan.sistema_de_restaurante.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservaService {
    private final ReservationRepository reservationRepository;
    private final MesaService mesaService;
    private final AuthUtil authUtil;

    private final UserService userService;
    private final ReservaMapper reservaMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, MesaService mesaService, AuthUtil authUtil, UserService userService, ReservaMapper reservaMapper) {
        this.reservationRepository = reservationRepository;
        this.mesaService = mesaService;
        this.authUtil = authUtil;
        this.userService = userService;
        this.reservaMapper = reservaMapper;
    }

    @Transactional
    @Override
    public ReservaResponseDto reservarMesa(UUID mesaId, ReservaRequestDto reservaRequestDto) {
        Integer quantidadePessoas = reservaRequestDto.quantidadePessoas();
        User user = authUtil.getUsuarioLogado();

        LocalDateTime dataReserva = reservaRequestDto.reservationDate();

        mesaService.validarMesaReserva(mesaId, quantidadePessoas);

        validarHorarioReserva(dataReserva);
        validarMesaDisponivelHorarioReserva(mesaId, dataReserva);

        Reservation reserva = new Reservation();
        Mesa mesa = mesaService.getMesa(mesaId);
        reserva.setMesa(mesa);
        reserva.setUser(user);
        reserva.setReservationDate(reservaRequestDto.reservationDate());
        reserva.setStatus(StatusReserva.ATIVO);

        Reservation reservaSalva = reservationRepository.save(reserva);

        mesaService.atualizarStatusMesaReservada(mesa.getId());

        return reservaMapper.entityToDtoResponse(reservaSalva);

    }

    @Override
    public List<ReservaResponseDto> listReservationsResponse() {
        User user = authUtil.getUsuarioLogado();
        List<Reservation> reserva = reservationRepository.findAllByUser(user);
        return reserva.stream().map(r -> reservaMapper.entityToDtoResponse(r)).
                collect(Collectors.toList());
    }

    @Override
    public void cancelReserva(UUID reservaId) {
        User user = authUtil.getUsuarioLogado();
        Reservation reserva = getReservaByUser(reservaId, user);

        reserva.setStatus(StatusReserva.CANCELADO);
        reservationRepository.save(reserva);
    }

    public void validarHorarioReserva(LocalDateTime dataReserva){
        LocalDateTime agora = LocalDateTime.now();
        if (dataReserva.isBefore(agora)){
            throw new IllegalArgumentException("A reserva precisa ser para um horário futuro.");
        }

        int hora = dataReserva.getHour();
        if (hora < 0 || hora >= 23){
            throw new IllegalArgumentException("A reserva deve ser feita dentro do horário de funcionamento (11:00 às 22:00).");

        }
    }

    public void validarMesaDisponivelHorarioReserva(UUID mesaId, LocalDateTime dataReserva){
        boolean existeReserva= reservationRepository.existsByMesaIdAndReservationDateAndStatus(
                mesaId, dataReserva, StatusReserva.ATIVO
        );

        if (existeReserva){
            throw new IllegalArgumentException("Mesa já reservada nesse horário.");
        }
    }

    private Reservation getReservaByUser(UUID id, User user){

        Reservation reserva = reservationRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma reserva encontrada."));
        return reserva;
    }

}
