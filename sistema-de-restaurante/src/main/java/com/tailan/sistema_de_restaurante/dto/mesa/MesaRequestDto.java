package com.tailan.sistema_de_restaurante.dto.mesa;

import com.tailan.sistema_de_restaurante.model.mesa.StatusMesa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MesaRequestDto(
        @NotBlank
        String name,
        @NotNull
        Integer capacity) {
}
