package com.tailan.sistema_de_restaurante.service;

import com.tailan.sistema_de_restaurante.model.usuario.Role;
import com.tailan.sistema_de_restaurante.model.usuario.RoleName;

import java.util.Optional;

public interface RoleService {

    Role buscarRole(RoleName  role);


}
