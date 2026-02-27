package com.pucmm.csti19105488.modelo;

public class Usuario {
    private String username;
    private String nombre;
    private String password;
    private Rol rol;
    private boolean activo;

    public Usuario(String username, String nombre, String password, Rol rol) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.activo = true;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActivo(boolean b) {
        this.activo = b;
    }

    public boolean isActivo() {
        return activo;
    }
}
