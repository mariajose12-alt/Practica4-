package com.pucmm.csti19105488.filtros;

import com.pucmm.csti19105488.modelo.Rol;
import com.pucmm.csti19105488.modelo.Usuario;
import io.javalin.http.Context;

public class SeguridadFiltro {

    // Requiere sesión iniciada
    public static void requiereLogin(Context ctx) {
        Usuario usuario = ctx.sessionAttribute("usuario");

        if (usuario == null) {
            ctx.redirect("/login");
            ctx.status(401);
            ctx.result("Debe iniciar sesión");
        }
    }

    // Requiere rol ADMIN o AUTOR
    public static void requiereAutor(Context ctx) {
        Usuario usuario = ctx.sessionAttribute("usuario");

        if (usuario == null) {
            ctx.redirect("/login");
            return;
        }

        if (usuario.getRol() != Rol.ADMIN && usuario.getRol() != Rol.AUTOR) {
            ctx.status(403);
            ctx.result("Acceso denegado");
        }
    }
}
