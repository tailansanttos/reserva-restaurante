package com.tailan.sistema_de_restaurante.controllers;

import com.tailan.sistema_de_restaurante.dto.api.ApiResponseDto;
import com.tailan.sistema_de_restaurante.dto.token.TokenResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserLoginDto;
import com.tailan.sistema_de_restaurante.dto.user.UserRegisterDto;
import com.tailan.sistema_de_restaurante.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Tag(name = "Gerenciamento de usuarios", description = "APIs para gerenciar usuarios")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registrar novo usuario", description = "Registrar um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuários criado com sucesso."
            ,content = @Content(schema = @Schema(implementation = UserRegisterDto.class))),
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        userService.createUser(userRegisterDto);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.CREATED, "Usuario cadastrado.");
        return ResponseEntity.ok(response);
    }


    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Usuário faz login", description = "Usuário realiza login e recebe token para ter acesso as outras requisições do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso."
                    ,content = @Content(schema = @Schema(implementation = UserLoginDto.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody @Valid UserLoginDto userLogin) {
        TokenResponseDto token = userService.authenticateUser(userLogin);
        ApiResponseDto response = new ApiResponseDto(token, "Seja bem vindo.");
        return ResponseEntity.ok(response);
    }
    }

