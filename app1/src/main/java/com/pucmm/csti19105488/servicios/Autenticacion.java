package com.pucmm.csti19105488.servicios;

import com.pucmm.csti19105488.modelo.Articulo;
import com.pucmm.csti19105488.modelo.Rol;
import com.pucmm.csti19105488.modelo.Usuario;
import com.pucmm.csti19105488.repositorio.UsuarioRepositorio;

public class Autenticacion {
    private UsuarioRepositorio usuarioRepo;

    public Autenticacion (UsuarioRepositorio usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    // Login
    public Usuario login(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        Usuario usuario = usuarioRepo.buscarPorUsername(username);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        return usuario;
    }

    // Roles
    public boolean esAdmin(Usuario usuario) {
        return usuario != null && usuario.getRol() == Rol.ADMIN;
    }

    public boolean esAutor(Usuario usuario) {
        return usuario != null && usuario.getRol() == Rol.AUTOR;
    }

    // Permisos
    public boolean puedeCrearArticulo(Usuario usuario) {
        return esAdmin(usuario) || esAutor(usuario);
    }

    public boolean puedeBorrarArticulo(Usuario usuario, Articulo articulo) {
        if (usuario == null || articulo == null) {
            return false;
        }

        if (esAdmin(usuario)) {
            return true;
        }

        // El autor solo puede borrar SUS artículos
        return articulo.getAutor().getUsername().equals(usuario.getUsername());
    }

    // Admin puede crear usuarios
    public boolean crearUsuario(String username, String nombre, String password, Rol rol) {

        if (usuarioRepo.buscarPorUsername(username) != null) {
            return false;
        }

        Usuario nuevo = new Usuario(username,nombre, password, rol);
        usuarioRepo.guardar(nuevo);
        return true;
    }

}
