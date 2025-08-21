package com.tailan.sistema_de_restaurante.dto.user;

import com.tailan.sistema_de_restaurante.model.usuario.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterDto(
        @NotBlank(message = "Campo nome é obrigatorio.")
        String name,
        @NotBlank(message = "Campo email é obrigatorio.")
        @Email
        String email,
        @NotBlank(message = "Campo password é obrigatorio.")
        String password,
        @NotNull(message = "Campo role é obrigatorio.")
        RoleName role) {
}

