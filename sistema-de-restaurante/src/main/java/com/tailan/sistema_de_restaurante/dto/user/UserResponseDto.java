package com.tailan.sistema_de_restaurante.dto.user;

import com.tailan.sistema_de_restaurante.model.usuario.Role;

import java.util.List;
import java.util.UUID;

public record UserResponseDto(UUID id, String email, List<Role> roles) {
}
