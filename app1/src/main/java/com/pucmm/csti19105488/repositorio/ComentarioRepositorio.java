package com.pucmm.csti19105488.repositorio;

import com.pucmm.csti19105488.modelo.Comentario;

import java.util.ArrayList;
import java.util.List;

public class ComentarioRepositorio {
    private List<Comentario> comentarios = new ArrayList<>();

    public void guardar(Comentario comentario) {
        comentarios.add(comentario);
    }

    public List<Comentario> listarPorArticuloId(long articuloId) {
        List<Comentario> resultado = new ArrayList<>();

        for (Comentario c : comentarios) {
            if (c.getArticulo().getId() == articuloId) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public Comentario buscarPorId(long comentarioId) {
        for (Comentario c : comentarios){
            if (c.getId() == comentarioId) return c;
        }
        return null;
    }

    public void eliminar(Comentario comentario) {
        comentarios.remove(comentario);
    }
}
