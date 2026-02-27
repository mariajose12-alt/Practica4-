<#macro base pageTitle="Mi Blog">
    <!DOCTYPE html>
    <html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>${pageTitle}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/css/styles.css" rel="stylesheet">
    </head>

    <body>

    <!-- NAVBAR -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
        <div class="container">
            <a class="navbar-brand" href="/">Mi Blog</a>

            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Inicio</a>
                </li>

                <#if usuario??>
                    <#if usuario.rol == "ADMIN" || usuario.rol == "AUTOR">
                        <li class="nav-item">
                            <a class="nav-link" href="/articulo/crear">Nuevo Artículo</a>
                        </li>
                    </#if>
                    <li class="nav-item">
                        <#if usuario.rol == "ADMIN">
                            <a class="nav-link" href="/usuario">${usuario.nombre}</a>
                        <#else>
                            <span class="nav-link">${usuario.nombre}</span>
                        </#if>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Salir</a>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Iniciar Sesión</a>
                    </li>
                </#if>

            </ul>
        </div>
    </nav>

    <!-- CONTENIDO DE LA VISTA -->
    <div class="container">
        <#nested>
    </div>

    <!-- FOOTER -->
    <footer class="text-center mt-5 mb-3">
        <p>&copy; 2026 Mi Blog - Práctica 2 ICC-352</p>
    </footer>

    </body>
    </html>
</#macro>
