package com.tailan.sistema_de_restaurante.service;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaRequestDto;
import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.model.mesa.Mesa;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface MesaService {
    public List<MesaResponseDto> getMesasDisponibles();
    public MesaResponseDto createMesa(MesaRequestDto mesaRequest);
    MesaResponseDto updateStatusMesa(UUID mesaId);
    void deleteMesa(UUID mesaId);
    public Mesa getMesa(UUID mesaId);


    Boolean validarMesaReserva(UUID mesaId, Integer quantidadePessoas);
    public void atualizarStatusMesaReservada(UUID mesaId);

    @Transactional
    void atualizarStatusMesaDisponivel(UUID mesaId);

    public MesaResponseDto entityToDto(Mesa mesa);
}