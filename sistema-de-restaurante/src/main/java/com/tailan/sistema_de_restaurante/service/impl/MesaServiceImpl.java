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

    @Transactional
    public void atualizarStatusMesaReservada(UUID mesaId){
        Mesa mesa = getMesa(mesaId);
        Boolean mesaDisponivel = validarMesaDisponivel(mesa.getId());
        if (mesaDisponivel) {
            mesa.setStatus(StatusMesa.RESERVADA);
            mesaRepository.save(mesa);
        }else throw new IllegalArgumentException("Mesa não disponivel para reserva.");

    }

    //CASO A MESA ESTEJA INATIVA, O USUARIO NAO CONSEGUE RESERVAR

    @Transactional
    public Boolean validarMesaDisponivel(UUID mesaId){
        Mesa mesa = getMesa(mesaId);
        if (mesa.getStatus().equals(StatusMesa.DISPONIVEL)){
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean validarCapacidadeMesa(UUID mesaId, Integer quantidadePessoas){
        Mesa mesa = getMesa(mesaId);
        if( quantidadePessoas > mesa.getCapacity()){
            return false;
        }
        return true;
    }

    private Mesa getMesa(UUID mesaId){
        Mesa mesa = mesaRepository.findById(mesaId).orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada."));
        return mesa;
    }

}
