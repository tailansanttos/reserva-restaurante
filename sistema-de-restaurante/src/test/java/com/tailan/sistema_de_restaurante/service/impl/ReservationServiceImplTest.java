package com.tailan.sistema_de_restaurante.service.impl;

import com.tailan.sistema_de_restaurante.dto.reserva.ReservaRequestDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaResponseDto;
import com.tailan.sistema_de_restaurante.mapper.ReservaMapper;
import com.tailan.sistema_de_restaurante.model.mesa.Mesa;
import com.tailan.sistema_de_restaurante.model.reserva.Reservation;
import com.tailan.sistema_de_restaurante.model.reserva.StatusReserva;
import com.tailan.sistema_de_restaurante.model.usuario.User;
import com.tailan.sistema_de_restaurante.repositories.ReservationRepository;
import com.tailan.sistema_de_restaurante.service.MesaService;
import com.tailan.sistema_de_restaurante.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MesaService mesaService;
    @Mock
    private AuthUtil authUtil;
    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservationServiceImpl reservationServiceImpl;

    private UUID reservaId;
    private UUID mesaId;
    private User usuario;
    private Mesa mesa;
    private Reservation reserva;
    private ReservaRequestDto reservaRequestDto;

    @BeforeEach
    public void setUp(){
        reservaId = UUID.randomUUID();
        mesaId = UUID.randomUUID();

        usuario = new User();
        usuario.setId(UUID.randomUUID());
        usuario.setEmail("teste@email.com");

        mesa = new Mesa();
        mesa.setId(mesaId);

        reserva = new Reservation();
        reserva.setId(reservaId);
        reserva.setMesa(mesa);
        reserva.setUser(usuario);
        reserva.setStatus(StatusReserva.ATIVO);

        reservaRequestDto = new ReservaRequestDto(LocalDateTime.now().plusHours(4), 5);
    }
    @Test
    void deveReservarMesaComSucesso() {
        when(authUtil.getUsuarioLogado()).thenReturn(usuario);
        when(mesaService.getMesa(any(UUID.class))).thenReturn(mesa);

        Reservation reservaSalva = new Reservation();
        reservaSalva.setMesa(mesa);
        reservaSalva.setUser(usuario);
        reservaSalva.setReservationDate(reservaRequestDto.reservationDate());
        reservaSalva.setStatus(StatusReserva.ATIVO);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservaSalva);


        ReservaResponseDto responseReserva = new ReservaResponseDto(null,null,null, null, UUID.randomUUID());
        when(reservaMapper.entityToDtoResponse(any(Reservation.class))).thenReturn(responseReserva);

        ReservaResponseDto resultado = reservationServiceImpl.reservarMesa(mesaId, reservaRequestDto);

        assertNotNull(resultado);

        verify(mesaService).validarMesaReserva(mesaId, reservaRequestDto.quantidadePessoas());
        verify(reservationRepository).save(any(Reservation.class));

        verify(mesaService).atualizarStatusMesaReservada(mesaId);
    }

    @Test
    void deveCancelarReservaMesaComSucesso() {
        when(authUtil.getUsuarioLogado()).thenReturn(usuario);
        when(reservationRepository.findByUserAndId(usuario,reservaId)).thenReturn(Optional.of(reserva));

        reservationServiceImpl.cancelReserva(reservaId);

        assertEquals(StatusReserva.CANCELADO, reserva.getStatus());

        verify(mesaService).atualizarStatusMesaDisponivel(reserva.getId());

        verify(reservationRepository).save(reserva);

    }
}