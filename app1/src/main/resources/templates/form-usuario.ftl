<#import "base.ftl" as layout>

<@layout.base pageTitle=(esCreacion?then("Crear Usuario","Editar Usuario"))>

    <div class="container my-5">

        <h2 class="text-center mb-4">
            <#if esCreacion>
                Crear Nuevo Usuario
            <#else>
                Editar Usuario
            </#if>
        </h2>

        <#if error??>
            <div class="alert alert-danger">
                ${error}
            </div>
        </#if>

        <form action="<#if esCreacion>/usuario/crear<#else>/usuario/editar/${usuarioForm.username}</#if>" method="post">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre"
                       value="${usuarioForm.nombre!}" required>
            </div>

            <div class="mb-3">
                <label for="username" class="form-label">Usuario</label>

                <#if esCreacion>
                    <input type="text" class="form-control" id="username" name="username"
                           value="${usuarioForm.username!}" required>
                <#else>
                    <input type="text" class="form-control"
                           value="${usuarioForm.username!}" readonly>
                    <input type="hidden" name="username"
                           value="${usuarioForm.username!}">
                </#if>
            </div>


            <div class="mb-3">
                <label for="password" class="form-label">
                    <#if esCreacion>Contraseña<#else>Contraseña (dejar vacío para no cambiar)</#if>
                </label>
                <input type="password" class="form-control" id="password" name="password"
                       <#if esCreacion>required</#if>>
            </div>

            <div class="mb-3">
                <label for="rol" class="form-label">Rol</label>
                <select class="form-select" id="rol" name="rol" required>
                    <option value="ADMIN" <#if usuarioForm.rol?string == "ADMIN">selected</#if>>ADMIN</option>
                    <option value="AUTOR" <#if usuarioForm.rol?string == "AUTOR">selected</#if>>AUTOR</option>
                    <option value="LECTOR" <#if usuarioForm.rol?string == "LECTOR">selected</#if>>LECTOR</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">
                <#if esCreacion>Crear Usuario<#else>Actualizar Usuario</#if>
            </button>
            <a href="/usuario" class="btn btn-secondary">Cancelar</a>
        </form>

    </div>

</@layout.base>
