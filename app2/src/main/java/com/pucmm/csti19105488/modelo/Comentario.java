package com.pucmm.csti19105488.modelo;

import com.pucmm.csti19105488.repositorio.ComentarioRepositorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comentario {
    private static long contador = 1;

    private long id;
    private Usuario autor;
    private String comentario;
    private Articulo articulo;
    private LocalDateTime fecha;

    public Comentario(Usuario autor, String comentario, Articulo articulo, LocalDateTime fecha) {
        this.id = contador++;
        this.autor = autor;
        this.comentario = comentario;
        this.articulo = articulo;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getComentario() {
        return comentario;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getFechaFormateada() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formatter);
    }

}
