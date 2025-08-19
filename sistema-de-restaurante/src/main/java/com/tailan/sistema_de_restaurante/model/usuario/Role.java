package com.tailan.sistema_de_restaurante.model.usuario;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    public Role(RoleName role, UUID id) {
        this.role = role;
        this.id = id;
    }

    public Role(RoleName role) {
        this.role = role;
    }

    public Role() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}
