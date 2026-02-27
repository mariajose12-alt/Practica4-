package com.pucmm.csti19105488.servicios;

import com.pucmm.csti19105488.modelo.*;
import com.pucmm.csti19105488.repositorio.ArticuloRepositorio;
import com.pucmm.csti19105488.repositorio.ComentarioRepositorio;
import com.pucmm.csti19105488.repositorio.EtiquetaRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class Blog {
    private ArticuloRepositorio articuloRepo;
    private EtiquetaRepositorio etiquetaRepo;
    private ComentarioRepositorio comentarioRepo;

    public Blog(ArticuloRepositorio articuloRepo, EtiquetaRepositorio etiquetaRepo, ComentarioRepositorio comentarioRepo) {
        this.articuloRepo = articuloRepo;
        this.etiquetaRepo = etiquetaRepo;
        this.comentarioRepo = comentarioRepo;
    }

    // Crear Artículo
    public boolean crearArticulo(String titulo, String cuerpo, String etiquetasTexto, Usuario autor) {

        Articulo articulo = new Articulo(titulo, cuerpo, autor);

        if (etiquetasTexto != null && !etiquetasTexto.isBlank()) {
            String[] etiquetas = etiquetasTexto.split(",");
            for (String e : etiquetas) {
                String limpia = e.trim();
                if (!limpia.isEmpty()) {
                    Etiqueta etiqueta = etiquetaRepo.guardarSiNoExiste(limpia);
                    articulo.getEtiquetas().add(etiqueta);
                }
            }
        }
        return articuloRepo.guardar(articulo);
    }

    // Listar Artículos
    public List<Articulo> listarArticulos() {
        return articuloRepo.listarOrdenadosPorFechaDesc();
    }

    // Buscar Artículos
    public Articulo buscarArticulo(long id) {
        return articuloRepo.buscarPorId(id);
    }

    //Editar Artículos
    public boolean editarArticulo(Articulo articulo, String titulo, String cuerpo, String[] etiquetasTexto) {

        // No existe, entonces no se edita
        if (articulo == null) {
            return false;
        }

        // Se cambian las características del artículo
        articulo.setTitulo(titulo);
        articulo.setCuerpo(cuerpo);
        articulo.getEtiquetas().clear();
        for(String e : etiquetasTexto) {
            articulo.addEtiqueta(new Etiqueta(e));
        }

        return articuloRepo.guardar(articulo);
    }

    // Agregar Comentario
    public void agregarComentario(Articulo articulo, Usuario autor, String contenido) {

        Comentario comentario = new Comentario(autor, contenido, articulo, LocalDateTime.now()); //Opcional: poner LocalDateTime.now() en el contructor, osea no pasarlo como parametro aqui
        comentarioRepo.guardar(comentario);
        articulo.getComentarios().add(comentario);
    }

    // Borrar Articulo
    public boolean puedeBorrar(Usuario usuario, Articulo articulo) {
        return usuario.getRol() == Rol.ADMIN || articulo.getAutor().getUsername().equals(usuario.getUsername());
    }

    public void borrarArticulo(Articulo articulo) {
        articuloRepo.eliminar(articulo);
    }

    public void eliminarComentario(Comentario comentario, Usuario usuario) {
        if (comentario.getAutor().equals(usuario) || usuario.getRol() == Rol.ADMIN) {
            // Quitar del repositorio global
            comentarioRepo.eliminar(comentario);

            // Quitar de la lista del artículo
            comentario.getArticulo().getComentarios().remove(comentario);
        }
    }

    public Comentario buscarComentarioPorId(long id) { return comentarioRepo.buscarPorId(id);}
}
