package com.tailan.sistema_de_restaurante.service;

import com.tailan.sistema_de_restaurante.dto.token.TokenResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserLoginDto;
import com.tailan.sistema_de_restaurante.dto.user.UserRegisterDto;

public interface UserService {

    public TokenResponseDto authenticateUser(UserLoginDto userLogin);
    public void createUser(UserRegisterDto userRegisterDto);

}
