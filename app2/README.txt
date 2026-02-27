Práctica 2 – CRUD

Aplicación web tipo blog desarrollada con Java, utilizando Javalin como framework web y
FreeMarker como motor de plantillas.

1. Descripción
    Este proyecto consiste en la implementación de un sistema básico de blog que permite:
        - Registro e inicio de sesión de usuarios
        - Gestión de roles (ADMIN y AUTOR)
        - Creación de artículos
        - Visualización de artículos en la página principal
        - Uso de etiquetas por artículo
        - Control de acceso según rol

La aplicación sigue una estructura basada en el patrón MVC para mantener separación entre lógica,
vista y controladores.

2. Tecnologías Utilizadas
    - Java
    - Javalin
    - FreeMarker
    - Bootstrap 5
    - Gradle

3.  Roles del Sistema
    - ADMIN:
        ~ Puede acceder al panel de usuario
        ~ Puede crear artículos
    - AUTOR:
        ~ Puede crear artículos
    - Usuario no autenticado:
        ~ Puede visualizar artículos
        ~ Puede iniciar sesión

4. Estructura del Proyecto
    El proyecto está organizado en:
        modelos → Entidades del sistema (Usuario, Artículo, etc.)
        controladores → Manejo de rutas y lógica de peticiones
        servicios → Lógica de negocio
        templates → Vistas en FreeMarker
        publico → Archivos estáticos (CSS)

5. Credenciales de prueba
    Usuario: admin
    Contraseña: admin

6. Contribuciones

    Historial:
    2b56cfb - maria - Corrección en eliminarComentario (Blog.java)
    4530059 - Almy - Readme.txt
    0d22154 - Almy - Mejora en presentacion del resumen de articulos
    0fa167f - Almy - Documentacion adicional
    5abb363 - Almy - Agregando documentacion y eliminacion de metodos no utilizados
    9a572c0 - maria - Corrección de permisos, errores y validaciones + mejora en el manejo de errores y formularios
    f8a7542 - Almy - Implementacion de gestion de usuarios
    d4dcaac - Almy - Formateo de la fecha en la vista index.ftl
    a06fa14 - Almy - Resolucion de problemas y mejoras e implementacion de nuevas funcionalidades en el proyecto de blog.
    ad1447a - Almy - Agrega estilos Bootstrap al index del blog
    9ff15fc - Almy - Blog activo, se muestran los articulos de manera descendente
    f5c9f8c - Almy - Configuracion de Freemarker y configuracion inici al de la pagina principal
    e6a32f6 - maria - Clase Comentario y Etiqueta Repositorio
    a7c5824 - maria - Lógica de negocio y autenticación
    22a9bed - maria - Estructura Basica

                            María José Cruz - 10154963 / Almy Ventura - 10153712
                                                GRUPO 6
                                        15 de febrero de 2026