package com.tailan.sistema_de_restaurante.mapper;

import com.tailan.sistema_de_restaurante.dto.user.UserResponseDto;
import com.tailan.sistema_de_restaurante.model.usuario.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
   public UserResponseDto entityToDto(User user){
        return new UserResponseDto(user.getId(), user.getEmail(), user.getRoles());
    }
}
