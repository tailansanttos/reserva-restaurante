package com.tailan.sistema_de_restaurante.mapper;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.model.mesa.Mesa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MesaMapper {
    public MesaResponseDto toMesaResponseDto(Mesa mesa) {
        return new MesaResponseDto(mesa.getId(), mesa.getName(), mesa.getCapacity(), mesa.getStatus());
    }

    public List<MesaResponseDto> toMesaDtoList(List<Mesa> mesas) {
        return mesas.stream().map(this::toMesaResponseDto).collect(Collectors.toList());
    }
}
