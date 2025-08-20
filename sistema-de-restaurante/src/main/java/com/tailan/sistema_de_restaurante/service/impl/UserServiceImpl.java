package com.tailan.sistema_de_restaurante.service.impl;

import com.tailan.sistema_de_restaurante.dto.token.TokenResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserLoginDto;
import com.tailan.sistema_de_restaurante.dto.user.UserRegisterDto;
import com.tailan.sistema_de_restaurante.dto.user.UserResponseDto;
import com.tailan.sistema_de_restaurante.infra.security.SecurityConfiguration;
import com.tailan.sistema_de_restaurante.infra.security.UserDetailsImpl;
import com.tailan.sistema_de_restaurante.mapper.UserMapper;
import com.tailan.sistema_de_restaurante.model.usuario.Role;
import com.tailan.sistema_de_restaurante.model.usuario.User;
import com.tailan.sistema_de_restaurante.repositories.UserRepository;
import com.tailan.sistema_de_restaurante.service.RoleService;
import com.tailan.sistema_de_restaurante.service.UserService;
import com.tailan.sistema_de_restaurante.service.auth.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfiguration securityConfiguration;
    private final RoleService  roleService;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, JwtTokenService tokenService, AuthenticationManager authenticationManager, SecurityConfiguration securityConfiguration, RoleService roleService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.securityConfiguration = securityConfiguration;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @Override
    public TokenResponseDto authenticateUser(UserLoginDto userLogin) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userLogin.email(), userLogin.password()
        );
        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new TokenResponseDto(tokenService.generateToken(userDetails));

    }

    @Override
    public void createUser(UserRegisterDto userRegisterDto) {
        String passwordEncripted = securityConfiguration.passwordEncoder().encode(userRegisterDto.password());
        Role role = roleService.buscarRole(userRegisterDto.role());

        User user = new User(
                userRegisterDto.name(),
                userRegisterDto.email(),
                passwordEncripted,
                Collections.singletonList(role));

        userRepository.save(user);


    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Usuario não cadastrado"));
        return user;
    }

    @Override
    public UserResponseDto entityToDto(User user) {
        return userMapper.entityToDto(user);
    }
}
