<#import "base.ftl" as layout>

<@layout.base pageTitle="Iniciar Sesión - Mi Blog">

    <div class="container my-5">

        <div class="login-container">

            <h2 class="text-center mb-4">Iniciar Sesión</h2>

            <!-- Mensaje de error si las credenciales son incorrectas -->
            <#if error??>
                <div class="alert alert-danger">
                    ${error}
                </div>
            </#if>

            <!-- FORMULARIO DE LOGIN -->
            <form method="POST" action="/login">

                <!-- Usuario -->
                <div class="mb-3">
                    <label for="username" class="form-label">Usuario:</label>
                    <input
                            type="text"
                            class="form-control"
                            id="username"
                            name="username"
                            placeholder="Ingresa tu usuario"
                            required>
                </div>

                <!-- Contraseña -->
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña:</label>
                    <input
                            type="password"
                            class="form-control"
                            id="password"
                            name="password"
                            placeholder="Ingresa tu contraseña"
                            required>
                </div>

                <!-- Botón de enviar -->
                <button type="submit" class="btn btn-primary w-100">
                    Iniciar Sesión
                </button>

            </form>

            <!-- Botón volver -->
            <div class="text-center mt-3">
                <a href="/" class="btn btn-secondary">
                    ← Volver al inicio
                </a>
            </div>

        </div>

    </div>

</@layout.base>