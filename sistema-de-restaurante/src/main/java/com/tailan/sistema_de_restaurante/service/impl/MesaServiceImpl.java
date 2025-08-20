package com.tailan.sistema_de_restaurante.service.impl;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaRequestDto;
import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.mapper.MesaMapper;
import com.tailan.sistema_de_restaurante.model.mesa.Mesa;
import com.tailan.sistema_de_restaurante.model.mesa.StatusMesa;
import com.tailan.sistema_de_restaurante.repositories.MesaRepository;
import com.tailan.sistema_de_restaurante.service.MesaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MesaServiceImpl implements MesaService {
    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    public MesaServiceImpl(MesaRepository mesaRepository, MesaMapper mesaMapper) {
        this.mesaRepository = mesaRepository;
        this.mesaMapper = mesaMapper;
    }

    @Override
    public List<MesaResponseDto> getMesasDisponibles() {
        List<Mesa> mesasDisponiveis = mesaRepository.findAllByStatus(StatusMesa.DISPONIVEL);
        return mesaMapper.toMesaDtoList(mesasDisponiveis);
    }

    @Override
    public MesaResponseDto createMesa(MesaRequestDto mesaRequest) {
        Mesa mesa = new Mesa();
        mesa.setName(mesaRequest.name());
        mesa.setCapacity(mesaRequest.capacity());
        mesa.setStatus(StatusMesa.DISPONIVEL);
        Mesa mesaSaved = mesaRepository.save(mesa);
        return mesaMapper.toMesaResponseDto(mesaSaved);
    }

    @Override
    public MesaResponseDto updateStatusMesa(UUID mesaId) {
        //ADMIN ATUALIZA O STATUS DA MESA PARA INATIVA CASO ELA QUEBRE.
        Optional<Mesa> mesa = mesaRepository.findById(mesaId);
        if (!(mesa.isPresent())) {
            throw new IllegalArgumentException("Mesa não encontrada no sistema.");
        }
        mesa.get().setStatus(StatusMesa.INATIVA);
        mesaRepository.save(mesa.get());
        return mesaMapper.toMesaResponseDto(mesa.get());
    }

    @Override
    public void deleteMesa(UUID mesaId) {
        Optional<Mesa> mesa = mesaRepository.findById(mesaId);
        if (!mesa.isPresent()) {
            throw new IllegalArgumentException("Mesa não encontrada no sistema.");
        }
        mesaRepository.delete(mesa.get());
    }

    @Override
    @Transactional
    public void atualizarStatusMesaReservada(UUID mesaId){
        Mesa mesa = getMesa(mesaId);
        mesa.setStatus(StatusMesa.RESERVADA);
        mesaRepository.save(mesa);

    }

    @Transactional
    @Override
    public void atualizarStatusMesaDisponivel(UUID mesaId){
        Mesa mesa = getMesa(mesaId);
        StatusMesa statusAntigo = mesa.getStatus();
        if (statusAntigo.equals(StatusMesa.RESERVADA)) {
            mesa.setStatus(StatusMesa.DISPONIVEL);
            mesaRepository.save(mesa);
        }else throw new IllegalArgumentException("Mesa não está reservada para ser atualizada.");

    }

    @Override
    public MesaResponseDto entityToDto(Mesa mesa) {
        return mesaMapper.toMesaResponseDto(mesa);
    }


    @Override
    public Boolean validarMesaReserva(UUID mesaId, Integer quantidadePessoas){
        Mesa mesa = getMesa(mesaId);

        if (!mesa.getStatus().equals(StatusMesa.DISPONIVEL)){
            throw new IllegalArgumentException("Mesa não está disponivel para reserva.");
        }

        if (quantidadePessoas > mesa.getCapacity()){
            throw new IllegalArgumentException("Capacidade da mesa insuficiente para a quantidade de pessoas.");
        }

        return true;
    }

    @Override
    public Mesa getMesa(UUID mesaId){
        Mesa mesa = mesaRepository.findById(mesaId).orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada."));
        return mesa;
    }

}
