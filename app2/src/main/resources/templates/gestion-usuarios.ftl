<#import "base.ftl" as layout>

<@layout.base pageTitle="Gestión de Usuarios - Mi Blog">

    <div class="container my-5">

        <h2 class="text-center mb-4">Gestión de Usuarios</h2>

        <div class="mb-4">
            <a href="/usuario/crear" class="btn btn-primary">
                + Crear Nuevo Usuario
            </a>
        </div>

        <div class="table-container">
            <table class="table">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Usuario</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <#list usuarios as usuario>
                    <tr>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.username}</td>
                        <td>
                            <span class="badge <#if usuario.rol == 'ADMIN'>bg-primary<#else>bg-secondary</#if> text-white">
                                ${usuario.rol}
                            </span>
                        </td>
                        <td>
                            <a href="/usuario/editar/${usuario.username}" class="btn btn-sm btn-outline-secondary">
                                Editar
                            </a>
                            <#if usuario.rol != "ADMIN">
                                <a href="/usuario/eliminar/${usuario.username}" class="btn btn-sm btn-outline-danger">
                                    Eliminar
                                </a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

    </div>

</@layout.base>