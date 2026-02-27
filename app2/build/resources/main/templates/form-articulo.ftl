<#import "base.ftl" as layout>

<@layout.base pageTitle="${esCreacion?then('Crear Artículo', 'Editar Artículo')} - Mi Blog">

    <div class="container my-5">

        <div class="form-container">

            <h2 class="text-center mb-4">
                <#if esCreacion>
                    Crear Nuevo Artículo
                <#else>
                    Editar Artículo
                </#if>
            </h2>

            <!-- Mensaje de error si existe -->
            <#if error??>
                <div class="alert alert-danger">
                    ${error}
                </div>
            </#if>

            <!-- FORMULARIO DE ARTÍCULO -->
            <form method="post"
                  action="${esCreacion?then('/articulo/crear', '/articulo/' + articulo.id + '/editar')}">

                <!-- Título -->
                <div class="mb-3">
                    <label for="titulo" class="form-label">Título del artículo:</label>
                    <input
                            type="text"
                            class="form-control"
                            id="titulo"
                            name="titulo"
                            value="${articulo.titulo!''}"
                            placeholder="Ingresa un título llamativo"
                            required>
                </div>

                <!-- Cuerpo -->
                <div class="mb-3">
                    <label for="cuerpo" class="form-label">Contenido:</label>
                    <textarea
                            class="form-control"
                            id="cuerpo"
                            name="cuerpo"
                            rows="10"
                            placeholder="Escribe el contenido de tu artículo aquí..."
                            required>${articulo.cuerpo!''}</textarea>
                </div>

                <!-- Etiquetas -->
                <div class="mb-3">
                    <label for="etiquetas" class="form-label">Etiquetas (separadas por comas):</label>
                    <input
                            type="text"
                            class="form-control"
                            id="etiquetas"
                            name="etiquetasTexto"
                            value="<#if articulo?? && articulo.etiquetas?? && articulo.etiquetas?size gt 0><#list articulo.etiquetas as etiqueta>${etiqueta.etiqueta}<#sep>, </#sep></#list></#if>"
                            placeholder="Ejemplo: tecnología, programación, web">
                    <small class="form-text text-muted">
                        Separa las etiquetas con comas. Ejemplo: java, spring, web
                    </small>
                </div>

                <!-- Botones -->
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">
                        <#if esCreacion>
                            Publicar Artículo
                        <#else>
                            Actualizar Artículo
                        </#if>
                    </button>

                    <a href="/" class="btn btn-secondary">
                        Cancelar
                    </a>
                </div>

            </form>

        </div>

    </div>

</@layout.base>
