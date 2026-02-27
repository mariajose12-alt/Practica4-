package com.pucmm.csti19105488.repositorio;

import com.pucmm.csti19105488.modelo.Usuario;
import org.eclipse.jetty.server.UserIdentity;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {
    private List<Usuario> usuarios = new ArrayList<>();

    public void guardar (Usuario usuario){
        usuarios.add(usuario);
    }

    public Usuario buscarPorUsername(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> listar() {
        return usuarios.stream()
                .filter(Usuario::isActivo)
                .toList();
    }

}
