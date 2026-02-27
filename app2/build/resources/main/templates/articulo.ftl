<#import "base.ftl" as layout>

<@layout.base pageTitle="${articulo.titulo!'Artículo'} - Mi Blog">

    <div class="container my-5">

        <#if articulo??>
            <!-- ARTÍCULO -->
            <div class="article-container">

                <!-- Título -->
                <h1>${articulo.titulo}</h1>

                <!-- Info del autor y fecha -->
                <div class="text-muted mb-4">
                    <small>
                        Publicado el ${articulo.fechaFormateada!"Fecha desconocida"}
                        por ${articulo.autor.nombre!"Anónimo"}
                    </small>
                </div>

                <!-- Etiquetas -->
                <#if articulo.etiquetas?? && articulo.etiquetas?size gt 0>
                    <div class="mb-4">
                        <#list articulo.etiquetas as etiqueta>
                            <span class="badge bg-primary">${etiqueta.etiqueta}</span>
                        </#list>
                    </div>
                </#if>

                <hr>

                <!-- Cuerpo del artículo -->
                <div class="article-body">
                    <p>${articulo.cuerpo}</p>
                </div>

                <!-- Botón volver -->
                <div class="mt-5">
                    <a href="/" class="btn btn-secondary">← Volver</a>
                </div>
            </div>

            <!-- COMENTARIOS -->
            <div class="comment-section mt-5">
                <h3 class="mb-4">Comentarios (${(articulo.comentarios?size)!0})</h3>
                <!-- Lista de comentarios -->
                <#if articulo.comentarios?? && articulo.comentarios?size gt 0>
                    <#list articulo.comentarios as comentario>
                        <div class="comment">
                            <div>
                                <span class="comment-author">
                                    ${comentario.autor.nombre!"Anónimo"}
                                </span>
                                <span class="comment-date">
                                    ${comentario.fechaFormateada}
                                </span>

                                <!-- Botón eliminar comentario -->
                                <#if usuario?? && (usuario.rol == "ADMIN" || comentario.autor.username == usuario.username)>
                                    <a href="/articulo/${articulo.id}/comentario/eliminar/${comentario.id}"
                                       class="btn btn-sm btn-outline-danger float-end">
                                        Eliminar
                                    </a>
                                </#if>
                            </div>
                            <p class="comment-text">
                                ${comentario.comentario!"(comentario vacío)"}
                            </p>
                        </div>
                    </#list>
                <#else>
                    <p class="text-muted">No hay comentarios todavía. ¡Sé el primero!</p>
                </#if>

                <!-- Formulario de comentario (solo para usuarios logueados) -->
                <#if usuario??>
                    <div class="mt-4">
                        <h4>Agregar comentario</h4>
                        <form method="post" action="/articulo/${articulo.id}/comentario">
                            <div class="mb-3">
                                <label for="contenido" class="form-label">Tu comentario:</label>
                                <textarea
                                        class="form-control"
                                        id="contenido"
                                        name="contenido"
                                        rows="4"
                                        placeholder="Escribe aquí tu comentario..."
                                        required></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Publicar comentario</button>
                        </form>
                    </div>
                <#else>
                    <div class="alert alert-info mt-4">
                        <a href="/login">Inicia sesión</a> para poder comentar este artículo
                    </div>
                </#if>
            </div>

        <#else>
            <!-- Error: artículo no existe -->
            <div class="text-center my-5">
                <h1>Artículo no disponible</h1>
                <p>El artículo que buscas no existe</p>
                <a href="/" class="btn btn-primary mt-3">Volver al inicio</a>
            </div>
        </#if>

    </div>

</@layout.base>
