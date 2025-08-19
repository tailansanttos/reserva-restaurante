package com.tailan.sistema_de_restaurante.controllers;

import com.tailan.sistema_de_restaurante.dto.api.ApiResponseDto;
import com.tailan.sistema_de_restaurante.dto.token.TokenResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserLoginDto;
import com.tailan.sistema_de_restaurante.dto.user.UserRegisterDto;
import com.tailan.sistema_de_restaurante.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        userService.createUser(userRegisterDto);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.CREATED, "Usuario cadastrado.");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody @Valid UserLoginDto userLogin) {
        TokenResponseDto token = userService.authenticateUser(userLogin);
        ApiResponseDto response = new ApiResponseDto(token, "Seja bem vindo.");
        return ResponseEntity.ok(response);
    }
    }

