package com.pucmm.csti19105488.modelo;

public class Etiqueta {
    private static long contador = 1;

    private long id;
    private String etiqueta;

    public Etiqueta(String etiqueta) {
        this.id = contador++;
        this.etiqueta = etiqueta;
    }

    public long getId() {
        return id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
