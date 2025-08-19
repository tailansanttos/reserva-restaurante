package com.tailan.sistema_de_restaurante.repositories;

import com.tailan.sistema_de_restaurante.model.usuario.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
   Optional<User> findByEmail(String email);
}
