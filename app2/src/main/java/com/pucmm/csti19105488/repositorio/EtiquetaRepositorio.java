package com.pucmm.csti19105488.repositorio;

import com.pucmm.csti19105488.modelo.Etiqueta;

import java.util.ArrayList;
import java.util.List;

public class EtiquetaRepositorio {
    private List<Etiqueta> etiquetas = new ArrayList<>();

    public Etiqueta buscarPorEtiqueta(String texto) {
        for (Etiqueta e : etiquetas) {
            if (e.getEtiqueta().equalsIgnoreCase(texto)) {
                return e;
            }
        }
        return null;
    }

    public Etiqueta guardarSiNoExiste(String texto) {
        Etiqueta existente = buscarPorEtiqueta(texto);
        if (existente != null) {
            return existente;
        }

        Etiqueta nueva = new Etiqueta(texto);
        etiquetas.add(nueva);
        return nueva;
    }

    public List<Etiqueta> listar() {
        return etiquetas;
    }
}
