package com.pucmm.csti19105488;

import com.pucmm.csti19105488.controladores.AutenticacionControlador;
import com.pucmm.csti19105488.controladores.BlogControlador;
import com.pucmm.csti19105488.controladores.UsuarioControlador;
import com.pucmm.csti19105488.filtros.SeguridadFiltro;
import com.pucmm.csti19105488.modelo.Rol;
import com.pucmm.csti19105488.modelo.Usuario;
import com.pucmm.csti19105488.repositorio.ArticuloRepositorio;
import com.pucmm.csti19105488.repositorio.ComentarioRepositorio;
import com.pucmm.csti19105488.repositorio.EtiquetaRepositorio;
import com.pucmm.csti19105488.repositorio.UsuarioRepositorio;
import com.pucmm.csti19105488.servicios.Autenticacion;
import com.pucmm.csti19105488.servicios.Blog;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinFreemarker;

public class Main {

    public static void main(String[] args) {

        // Repositorios en memoria
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
        ArticuloRepositorio articuloRepo = new ArticuloRepositorio();
        EtiquetaRepositorio etiquetaRepo = new EtiquetaRepositorio();
        ComentarioRepositorio comentarioRepo = new ComentarioRepositorio();

        // Usuario administrador por defecto
        Usuario admin = new Usuario("admin", "admin", "admin", Rol.ADMIN);
        usuarioRepo.guardar(admin);

        // Servicios
        Autenticacion authService = new Autenticacion(usuarioRepo);
        Blog blogService = new Blog(articuloRepo, etiquetaRepo, comentarioRepo);

        // Configuración del servidor
        Javalin app = Javalin.create(config -> {

            // Archivos estáticos
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            });

            // Plantillas Freemarker
            config.fileRenderer(new JavalinFreemarker());
        });

        /*  FILTROS DE SEGURIDAD  */

        // Crear / editar / eliminar artículos
        app.before("/articulo/crear", SeguridadFiltro::requiereAutor);
        app.before("/articulo/*/editar", SeguridadFiltro::requiereAutor);
        app.before("/articulo/*/eliminar", SeguridadFiltro::requiereAutor);

        // Agregar comentarios
        app.before("/articulo/*/comentario", SeguridadFiltro::requiereLogin);

        /* CONTROLADORES  */

        new AutenticacionControlador(app, authService);
        new BlogControlador(app, blogService,authService, articuloRepo);
        new UsuarioControlador(app,usuarioRepo, authService);

//        /*  DATOS DE PRUEBA  */
//
//        blogService.crearArticulo(
//                "Titulo de prueba",
//                "Cuerpo de prueba, solo los primeros 70 caracteres deben ser visibles en el preview del texto.",
//                "etiqueta1, etiqueta2",
//                admin
//        );
//
//        blogService.crearArticulo(
//                "Otro post",
//                "No se que decir",
//                "javalin",
//                admin
//        );

        // Iniciar servidor
        app.start(9090);
    }
}
