package com.pucmm.csti19105488.controladores;

import com.pucmm.csti19105488.modelo.Usuario;
import com.pucmm.csti19105488.servicios.Autenticacion;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class AutenticacionControlador {

    private Autenticacion authService;

    public AutenticacionControlador(Javalin app, Autenticacion authService) {
        this.authService = authService;

        // Mostrar formulario de login
        app.get("/login", ctx -> {

            if (ctx.sessionAttribute("usuario") != null) {
                ctx.redirect("/");
                return;
            }

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario", ctx.sessionAttribute("usuario"));
            modelo.put("error",null);
            ctx.render("templates/login.ftl", modelo);
        });

        // Procesar login
        app.post("/login", ctx -> {

            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            Usuario usuario = authService.login(username, password);

            if (usuario == null) {
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("error", "Usuario o contraseña incorrectos");
                ctx.render("templates/login.ftl", modelo);
                return;
            }

            ctx.sessionAttribute("usuario", usuario);
            ctx.redirect("/");
        });

        // Cerrar sesión
        app.get("/logout", ctx -> {
            ctx.sessionAttribute("usuario", null);
            ctx.redirect("/");
        });
    }
}
