package com.tailan.sistema_de_restaurante.util;

import com.tailan.sistema_de_restaurante.infra.security.UserDetailsImpl;
import com.tailan.sistema_de_restaurante.model.usuario.User;
import com.tailan.sistema_de_restaurante.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    private final UserService  userService;

    public AuthUtil(UserService userService) {
        this.userService = userService;
    }

    public User getUsuarioLogado(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl){
            String email = ((UserDetailsImpl) principal).getUsername();
            return userService.findByEmail(email);
        }
        throw new RuntimeException("Usuario não autenticado");
    }
}
