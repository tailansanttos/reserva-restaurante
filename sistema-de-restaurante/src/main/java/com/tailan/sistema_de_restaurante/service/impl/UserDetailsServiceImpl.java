package com.tailan.sistema_de_restaurante.service.impl;

import com.tailan.sistema_de_restaurante.infra.security.UserDetailsImpl;
import com.tailan.sistema_de_restaurante.model.usuario.User;
import com.tailan.sistema_de_restaurante.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserDetailsImpl(user);


    }
}
