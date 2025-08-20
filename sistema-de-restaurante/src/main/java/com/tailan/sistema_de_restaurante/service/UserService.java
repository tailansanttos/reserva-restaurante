package com.tailan.sistema_de_restaurante.service;

import com.tailan.sistema_de_restaurante.dto.token.TokenResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserLoginDto;
import com.tailan.sistema_de_restaurante.dto.user.UserRegisterDto;
import com.tailan.sistema_de_restaurante.dto.user.UserResponseDto;
import com.tailan.sistema_de_restaurante.model.usuario.User;

public interface UserService {

    public TokenResponseDto authenticateUser(UserLoginDto userLogin);
    public void createUser(UserRegisterDto userRegisterDto);
    public User findByEmail(String email);

    public UserResponseDto entityToDto(User user);

}
