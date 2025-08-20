package com.tailan.sistema_de_restaurante.infra.security;

import com.tailan.sistema_de_restaurante.model.usuario.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;

import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails{

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //converte a lista de roles do usuario, em uma coleção de GrantedAuthorities, forma que o Spring security usa para representar os papeis.
       return user.getRoles()
               .stream().map(role -> new SimpleGrantedAuthority(role.getRole().name()))
               .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }

    public User getUser() {
        return user;
    }
}
