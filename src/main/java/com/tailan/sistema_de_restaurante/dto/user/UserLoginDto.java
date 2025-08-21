package com.tailan.sistema_de_restaurante.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank(message = "Campo é obrigatorio.")
        String email,
        @NotBlank(message = "Campo senha é obrigatorio.")
        String password) {
}
