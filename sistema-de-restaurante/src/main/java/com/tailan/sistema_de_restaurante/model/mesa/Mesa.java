package com.tailan.sistema_de_restaurante.model.mesa;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Integer capacity;
    private StatusMesa status;

    public Mesa() {

    }

    public Mesa(String name, Integer capacity, StatusMesa status) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public StatusMesa getStatus() {
        return status;
    }

    public void setStatus(StatusMesa status) {
        this.status = status;
    }
}
