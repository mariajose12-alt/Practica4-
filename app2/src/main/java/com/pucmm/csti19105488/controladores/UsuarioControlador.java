package com.pucmm.csti19105488.controladores;

import com.pucmm.csti19105488.modelo.Rol;
import com.pucmm.csti19105488.modelo.Usuario;
import com.pucmm.csti19105488.repositorio.UsuarioRepositorio;
import com.pucmm.csti19105488.servicios.Autenticacion;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsuarioControlador {

    private UsuarioRepositorio usuarioRepo;
    private Autenticacion authService;

    public UsuarioControlador(Javalin app, UsuarioRepositorio usuarioRepo, Autenticacion authService) {
        this.usuarioRepo = usuarioRepo;
        this.authService = authService;

        // LISTAR USUARIOS
        app.get("/usuario", ctx -> {
            Usuario admin = ctx.sessionAttribute("usuario");
            if (!authService.esAdmin(admin)) {
                ctx.status(403).result("No tienes permisos para acceder a esta sección");
                return;
            }

            List<Usuario> usuarios = this.usuarioRepo.listar();
            Map<String, Object> model = new HashMap<>();
            model.put("usuarios", usuarios);
            model.put("usuario", ctx.sessionAttribute("usuario"));
            ctx.render("templates/gestion-usuarios.ftl", model);
        });

        // FORMULARIO CREAR USUARIO
        app.get("/usuario/crear", ctx -> {
            Usuario admin = ctx.sessionAttribute("usuario");
            if (!authService.esAdmin(admin)) {
                ctx.status(403).result("No tienes permisos para acceder a esta sección");
                return;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("usuario", admin);
            model.put("usuarioForm", new Usuario(null,null,null, Rol.LECTOR));
            model.put("esCreacion", true);
            ctx.render("templates/form-usuario.ftl", model);
        });

        // CREAR USUARIO
        app.post("/usuario/crear", ctx -> {
            Usuario admin = ctx.sessionAttribute("usuario");
            if (!authService.esAdmin(admin)) {
                ctx.status(403).result("No tienes permisos para realizar esta acción");
                return;
            }

            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            String nombre = ctx.formParam("nombre");
            String rolStr = ctx.formParam("rol");
            Rol rol = Rol.valueOf(rolStr.toUpperCase());

            boolean creado = authService.crearUsuario(username, nombre, password, rol);

            if (!creado) {
                Map<String, Object> model = new HashMap<>();
                model.put("usuario", admin);
                model.put("usuarioForm", new Usuario(username, nombre, password, rol));
                model.put("esCreacion", true);
                model.put("error", "El usuario ya existe.");

                ctx.render("templates/form-usuario.ftl", model);
                return;
            }

            ctx.redirect("/usuario");
        });

        // FORMULARIO EDITAR USUARIO
        app.get("/usuario/editar/{username}", ctx -> {
            Usuario admin = ctx.sessionAttribute("usuario");
            if (!authService.esAdmin(admin)) {
                ctx.status(403).result("No tienes permisos para acceder a esta sección");
                return;
            }

            String username = ctx.pathParam("username");
            Usuario usuario = this.usuarioRepo.buscarPorUsername(username);

            if (usuario == null) {
                ctx.status(404).result("Usuario no encontrado");
                return;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("usuario", admin);
            model.put("usuarioForm", usuario);
            model.put("esCreacion", false);
            ctx.render("templates/form-usuario.ftl", model);
        });

        // EDITAR USUARIO
        // Se cambió {id} por username porque se estaba pasando id pero trabajando con username
        app.post("/usuario/editar/{username}", ctx -> {
            Usuario admin = ctx.sessionAttribute("usuario");
            if (!authService.esAdmin(admin)) {
                ctx.status(403).result("No tienes permisos para realizar esta acción");
                return;
            }

            String username = ctx.pathParam("username");
            Usuario usuario = this.usuarioRepo.buscarPorUsername(username);

            if (usuario == null) {
                ctx.status(404).result("Usuario no encontrado");
                return;
            }

            usuario.setNombre(ctx.formParam("nombre"));

            String password = ctx.formParam("password");
            if (password != null && !password.isEmpty()) {
                usuario.setPassword(password);
            }

            String rolStr = ctx.formParam("rol");
            usuario.setRol(Rol.valueOf(rolStr.toUpperCase()));

            ctx.redirect("/usuario");
        });

        // ELIMINACIÓN LÓGICA
        app.get("/usuario/eliminar/{username}", ctx -> {
            Usuario admin = ctx.sessionAttribute("usuario");
            if (!authService.esAdmin(admin)) {
                ctx.status(403).result("No tienes permisos para realizar esta acción");
                return;
            }

            String username = ctx.pathParam("username");
            Usuario usuario = this.usuarioRepo.buscarPorUsername(username);

            if (usuario == null) {
                ctx.status(404).result("Usuario no encontrado");
                return;
            }

            // Nunca eliminar a otro ADMIN
            if (usuario.getRol() == Rol.ADMIN) {
                ctx.status(403).result("No se puede eliminar a un administrador");
                return;
            }

            usuario.setActivo(false); // eliminación lógica
            ctx.redirect("/usuario");
        });
    }
}
