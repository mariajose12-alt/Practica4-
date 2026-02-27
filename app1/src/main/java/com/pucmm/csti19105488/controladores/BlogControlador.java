package com.pucmm.csti19105488.controladores;

import com.pucmm.csti19105488.modelo.Articulo;
import com.pucmm.csti19105488.modelo.Comentario;
import com.pucmm.csti19105488.modelo.Usuario;
import com.pucmm.csti19105488.repositorio.ArticuloRepositorio;
import com.pucmm.csti19105488.repositorio.ComentarioRepositorio;
import com.pucmm.csti19105488.servicios.Autenticacion;
import com.pucmm.csti19105488.servicios.Blog;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class BlogControlador {

    private Blog blogService;
    private Autenticacion authService;
    private ArticuloRepositorio articuloRepo;

    public BlogControlador(Javalin app, Blog blogService, Autenticacion authService, ArticuloRepositorio articuloRepo) {
        this.blogService = blogService;
        this.authService =  authService;
        this.articuloRepo = articuloRepo;

        // Página principal
        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();

            var articulos = articuloRepo.listarOrdenadosPorFechaDesc();
            if (articulos == null) articulos = new ArrayList<>(); // evita null
            model.put("articulos", articulos);

            model.put("usuario", ctx.sessionAttribute("usuario"));
            ctx.render("templates/index.ftl", model);
        });

        // RUTAS DE ARTÍCULO

        // Formulario crear artículo
        app.get("/articulo/crear", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");

            // Validar que el usuario esté logueado
            if (usuario == null) {
                ctx.redirect("/login");
                return;
            }

            // Validar permisos para crear artículo
            if (!authService.puedeCrearArticulo(usuario)) {
                ctx.status(403);
                ctx.result("No tienes permisos para crear este artículo");
                ctx.redirect("/"); // redirige al index en lugar de renderizar /crear
                return;
            }

            // Preparar modelo para el formulario
            Map<String, Object> model = new HashMap<>();
            model.put("usuario", usuario);
            model.put("articulo", new Articulo(null, null, null));
            model.put("esCreacion", true);

            ctx.render("templates/form-articulo.ftl", model);
        });

        // Procesar creación de artículo
        app.post("/articulo/crear", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");

            // Validar usuario logueado
            if (usuario == null) {
                ctx.redirect("/login");
                return;
            }

            // Validar permisos para crear artículo
            if (!authService.puedeCrearArticulo(usuario)) {
                ctx.status(403);
                ctx.result("No tienes permisos para crear este artículo");
                ctx.redirect("/"); // opcional: redirige al index
                return;
            }

            // Tomar datos del formulario
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetasTexto");

            // Validar que los campos no sean vacíos
            if (titulo == null || titulo.isBlank() || cuerpo == null || cuerpo.isBlank()) {
                ctx.status(400).result("Título y cuerpo son obligatorios");
                return;
            }

            // Crear artículo usando el servicio
            boolean creado = blogService.crearArticulo(titulo, cuerpo, etiquetas, usuario);

            if (!creado) {
                Map<String, Object> model = new HashMap<>();
                model.put("usuario", usuario);
                model.put("articulo", new Articulo(titulo, cuerpo, usuario));
                model.put("esCreacion", true);
                model.put("error", "Ya existe un artículo con ese título.");

                ctx.render("templates/form-articulo.ftl", model);
                return;
            }

            // Redirigir al index
            ctx.redirect("/");

        });

        // Formulario editar artículo
        app.get("/articulo/{id}/editar", ctx -> {
            long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuario = ctx.sessionAttribute("usuario");
            Articulo articulo = blogService.buscarArticulo(id);

            if (articulo == null) {
                ctx.status(404).result("Artículo no encontrado");
                return;
            }

            if (!blogService.puedeBorrar(usuario, articulo)) {
                ctx.status(403).result("No autorizado");
                return;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("articulo", articulo);
            model.put("usuario", usuario);
            model.put("esCreacion", false);
            ctx.render("templates/form-articulo.ftl", model);
        });

        // Procesar edición
        app.post("/articulo/{id}/editar", ctx -> {
            long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuario = ctx.sessionAttribute("usuario");
            Articulo articulo = blogService.buscarArticulo(id);

            if (articulo == null || !blogService.puedeBorrar(usuario, articulo)) {
                ctx.status(404).result("Artículo no encontrado");
                return;
            }

            if(!blogService.puedeBorrar(usuario, articulo)) {
                ctx.status(403).result("No autorizado");
                return;
            }

            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetasTexto = ctx.formParam("etiquetasTexto");
            String[] etiquetas = etiquetasTexto.split(",");

            blogService.editarArticulo(articulo, titulo, cuerpo, etiquetas);
            ctx.redirect("/articulo/" + id);
        });

        // Eliminar artículo
        app.get("/articulo/{id}/eliminar", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");
            long id = Long.parseLong(ctx.pathParam("id"));
            Articulo articulo = blogService.buscarArticulo(id);

            if (articulo == null) {
                ctx.status(404).result("Artículo no encontrado");
                return;
            }

            if (!authService.puedeBorrarArticulo(usuario, articulo)) {
                ctx.status(403).result("No autorizado");
                return;
            }

            blogService.borrarArticulo(articulo);
            ctx.redirect("/");
        });

        //  RUTAS DE COMENTARIOS
        app.post("/articulo/{id}/comentario", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");
            long id = Long.parseLong(ctx.pathParam("id"));
            Articulo articulo = blogService.buscarArticulo(id);

            if (usuario == null || articulo == null) {
                ctx.redirect("/login");
                return;
            }

            String contenido = ctx.formParam("contenido");

            if (contenido == null || contenido.isBlank()) {
                ctx.redirect("/articulo/" + id);
                return;
            }

            blogService.agregarComentario(articulo, usuario, contenido);
            ctx.redirect("/articulo/" + id);
        });

        app.get("/articulo/{articuloId}/comentario/eliminar/{comentarioId}", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");
            long articuloId = Long.parseLong(ctx.pathParam("articuloId"));
            long comentarioId = Long.parseLong(ctx.pathParam("comentarioId"));

            Articulo articulo = blogService.buscarArticulo(articuloId);
            Comentario comentario = blogService.buscarComentarioPorId(comentarioId);

            if (comentario == null) {
                ctx.status(404).result("Comentario no encontrado");
                return;
            }

            if (usuario == null) {
                ctx.redirect("/login");
                return;
            }

            blogService.eliminarComentario(comentario, usuario);
            ctx.redirect("/articulo/" + articuloId);
        });

        //  RUTA  DE VER ARTÍCULO
        app.get("/articulo/{id}", ctx -> {
            long id = Long.parseLong(ctx.pathParam("id"));
            Articulo articulo = blogService.buscarArticulo(id);

            if (articulo == null) {
                ctx.status(404).result("Artículo no encontrado");
                return;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("articulo", articulo);
            model.put("usuario", ctx.sessionAttribute("usuario"));
            ctx.render("templates/articulo.ftl", model);
        });
    }
}
