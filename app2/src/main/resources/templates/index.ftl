<#import "base.ftl" as layout>

<@layout.base pageTitle="Inicio - Mi Blog">

    <!-- HEADER -->
    <div class="blog-header">
        <div class="container text-center">
            <h1>Mi Blog Personal</h1>
            <p>Artículos y pensamientos</p>
        </div>
    </div>

    <!-- CONTENIDO -->
    <div class="container my-5">

        <!-- Mensaje si está logueado -->
        <#if usuario??>
            <div class="alert alert-success">
                Hola de nuevo, <strong>${usuario.nombre}</strong>
            </div>
        </#if>

        <!-- Si no hay artículos -->
        <#if articulos?size == 0>
            <div class="text-center my-5">
                <h2>No hay artículos todavía</h2>
                <p>Sé el primero en crear uno</p>
                <#if usuario?? && (usuario.rol == "ADMIN" || usuario.rol == "AUTOR")>
                    <a href="/articulo/crear" class="btn btn-primary mt-3">
                        Crear primer artículo
                    </a>
                </#if>
            </div>
        <#else>
            <!-- LISTA DE ARTÍCULOS -->
            <div class="row">
                <#list articulos as articulo>
                    <div class="col-md-6 mb-4">
                        <div class="card h-100">
                            <div class="card-body">

                                <!-- Título -->
                                <h3 class="card-title">
                                    <a href="/articulo/${articulo.id}" class="text-dark">
                                        ${articulo.titulo}
                                    </a>
                                </h3>

                                <!-- Autor y fecha -->
                                <p class="text-muted">
                                    <small>
                                        Por: ${articulo.autor.nombre!"Anónimo"}
                                        <#if articulo.fecha??>
                                            ${articulo.fechaFormateada}
                                        </#if>
                                    </small>
                                </p>

                                <!-- Etiquetas -->
                                <#if articulo.etiquetas?? && articulo.etiquetas?size gt 0>
                                    <div class="mb-3">
                                        <#list articulo.etiquetas as etiqueta>
                                            <span class="badge bg-secondary text-white me-1">${etiqueta.etiqueta}</span>
                                        </#list>
                                    </div>
                                </#if>

                                <!-- Resumen -->
                                <p class="card-text">
                                    <#if articulo.cuerpo?length gt 70>
                                        ${articulo.resumen}
                                    <#else>
                                        ${articulo.cuerpo}
                                    </#if>
                                </p>

                                <!-- Botón leer más -->
                                <a href="/articulo/${articulo.id}" class="btn btn-primary">
                                    Leer más →
                                </a>

                                <!-- Botones de editar/eliminar -->
                                <#if usuario?? && (usuario.rol == "ADMIN" || (usuario.rol == "AUTOR" && articulo.autor.username == usuario.username))>
                                    <div class="mt-2">
                                        <a href="/articulo/${articulo.id}/editar"
                                           class="btn btn-sm btn-outline-secondary">
                                            Editar
                                        </a>
                                        <a href="/articulo/${articulo.id}/eliminar"
                                           class="btn btn-sm btn-outline-danger">
                                            Eliminar
                                        </a>
                                    </div>
                                </#if>

                                <!-- Contador de comentarios -->
                                <#if articulo.comentarios??>
                                    <p class="text-muted mt-2">
                                        <small>💬 ${articulo.comentarios?size} comentarios</small>
                                    </p>
                                </#if>

                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>

    </div>

</@layout.base>