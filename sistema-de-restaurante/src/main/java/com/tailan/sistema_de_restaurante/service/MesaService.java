package com.tailan.sistema_de_restaurante.service;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaRequestDto;
import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.model.mesa.Mesa;

import java.util.List;
import java.util.UUID;

public interface MesaService {
    public List<MesaResponseDto> getMesasDisponibles();
    public MesaResponseDto createMesa(MesaRequestDto mesaRequest);
    MesaResponseDto updateStatusMesa(UUID mesaId);
    void deleteMesa(UUID mesaId);
}
