package com.pucmm.csti19105488.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Articulo {
    private static long contador = 1;
    private long id;
    private String titulo;
    private String cuerpo;
    private Usuario autor;
    private List<Etiqueta> etiquetas = new ArrayList<>();
    private List<Comentario> comentarios = new ArrayList<>();
    private LocalDateTime fecha;

    public Articulo(String titulo, String cuerpo, Usuario autor) {
        this.id = contador++;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void setEtiquetas(String[] etiquetasTexto) {
    }

    public void addEtiqueta(Etiqueta etiqueta) { etiquetas.add(etiqueta);}

    public String getFechaFormateada() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formatter);
    }

    public String getResumen() {
        if (cuerpo == null) return "";
        return cuerpo.length() > 70
                ? cuerpo.substring(0, 70) + "..."
                : cuerpo;
    }

}
