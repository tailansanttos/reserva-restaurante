package com.tailan.sistema_de_restaurante.repositories;

import com.tailan.sistema_de_restaurante.model.usuario.Role;
import com.tailan.sistema_de_restaurante.model.usuario.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRole(RoleName role);
}
