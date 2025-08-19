package com.tailan.sistema_de_restaurante.model.usuario;

public enum UserRole {
    ADMIN("admin"),
    USER("user");
    private String value;
    UserRole(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
