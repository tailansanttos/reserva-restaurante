package com.tailan.sistema_de_restaurante.repositories;

import com.tailan.sistema_de_restaurante.model.mesa.Mesa;
import com.tailan.sistema_de_restaurante.model.mesa.StatusMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, UUID> {
    List<Mesa> findAllByStatus(StatusMesa status);
}
