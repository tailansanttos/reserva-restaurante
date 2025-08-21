package com.tailan.sistema_de_restaurante.service.impl;

import com.tailan.sistema_de_restaurante.model.usuario.Role;
import com.tailan.sistema_de_restaurante.model.usuario.RoleName;
import com.tailan.sistema_de_restaurante.repositories.RoleRepository;
import com.tailan.sistema_de_restaurante.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role buscarRole(RoleName role) {
        Optional<Role> buscarRole = roleRepository.findByRole(role);
        if (buscarRole.isPresent()) {
            return buscarRole.get();
        }
        Role newRole = new Role(role);
        Role savedRole = roleRepository.save(newRole);
        return savedRole;
    }
}
