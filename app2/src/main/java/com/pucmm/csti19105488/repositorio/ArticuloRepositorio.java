package com.pucmm.csti19105488.repositorio;

import com.pucmm.csti19105488.modelo.Articulo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArticuloRepositorio {
    private List<Articulo> articulos = new ArrayList<>();

    public boolean guardar (Articulo articulo){
        for (Articulo a : articulos) {
            if (a.getTitulo().equalsIgnoreCase(articulo.getTitulo())){
                return false;
            }
        }
        articulos.add(articulo);
        return true;
    }

    // Comparator es una interfaz que ayuda a comparar dos objetos al momento de ordenarlos
    public List<Articulo> listarOrdenadosPorFechaDesc() {
        articulos.sort(
                Comparator.comparing(Articulo::getFecha).reversed()
        );
        return articulos;
    }

    public Articulo buscarPorId(long id) {
        for (Articulo a : articulos) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public void eliminar(Articulo articulo) {
        articulos.remove(articulo);
    }
}
