package com.tailan.sistema_de_restaurante.dto.api;

import java.time.LocalDateTime;

public record ErrorResponseDto(LocalDateTime timesTamp, Integer status, String error, String message) {
}
