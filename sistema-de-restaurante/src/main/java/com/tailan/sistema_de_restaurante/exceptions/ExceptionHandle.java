package com.tailan.sistema_de_restaurante.exceptions;

import com.tailan.sistema_de_restaurante.dto.api.ApiResponseDto;
import com.tailan.sistema_de_restaurante.dto.api.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFoundException(UserNotFoundException userNotFoundException) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Usuário não encontrado.");
        return  new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MesaNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> mesaNotFoundException(MesaNotFoundException mesaNotFoundException) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Mesa não encontrado.");
        return  new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MesaIndisponivelException.class)
    public ResponseEntity<ErrorResponseDto> mesaIndisponivelException(MesaIndisponivelException mesaIndisponivelException) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Mesa indisponivel para reserva.");
        return  new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(CapacidadeMesaException.class)
    public ResponseEntity<ErrorResponseDto> capacidadeMesaException(CapacidadeMesaException capacidadeMesaException) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Quantidade de pessoa atinge o maximo que a mesa permite.");
        return  new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReservationIndisponivelException.class)
    public ResponseEntity<ErrorResponseDto> reservationIndisponivelException(ReservationIndisponivelException reservationIndisponivelException) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Horário da reserva indisponivel.");
        return  new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> reservationNotFoundException(ReservationNotFoundException reservationNotFoundException) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Nenhuma reserva encontrada.");
        return  new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}
