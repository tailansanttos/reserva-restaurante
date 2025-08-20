package com.tailan.sistema_de_restaurante.dto.mesa;

import com.tailan.sistema_de_restaurante.model.mesa.StatusMesa;

import java.util.UUID;

public record MesaResponseDto(UUID mesaId, String name, Integer capacity, StatusMesa status) {
}
