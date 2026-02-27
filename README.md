# Práctica 4 – HTTPS - Letsencrypt - VirtualHost 

## Descripción

En esta práctica se realizó el despliegue de dos aplicaciones Java desarrolladas con Javalin en un entorno Linux (Ubuntu Server) dentro de una instancia EC2 en AWS.

Se configuró Apache como **reverse proxy** para servir ambas aplicaciones bajo distintos subdominios y se implementó **HTTPS** utilizando certificados SSL generados con Let's Encrypt mediante Certbot.

---

# Video Demostrativo
Enlace principal: https://www.youtube.com/watch?v=VlaAigtA1IU

Enlace alternativo disponible en OneDrive: https://cepucmmedu-my.sharepoint.com/:v:/g/personal/mjcg0003_ce_pucmm_edu_do/IQCmOZl8Tt6YQryxyDCc_yPzAUuIpyxF2JdhCvzpS7pRPJk?e=JrS4Kd&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D

---

## Tecnologías Utilizadas

- Java 17
- Javalin
- Gradle
- Apache 2.4
- AWS EC2 (Ubuntu Server)
- Namecheap (DNS)
- Certbot (Let's Encrypt SSL)

---

## Arquitectura General

- app1 → corre internamente en puerto 7000
- app2 → corre internamente en puerto 9090
- Apache escucha en 80 (HTTP) y 443 (HTTPS)
- Apache redirige tráfico hacia los puertos internos usando ProxyPass
- Certbot gestiona certificados SSL para ambos dominios

---

### Ejecutar Script de Configuracion
El proyecto incluye un script que prepara automáticamente el entorno.
```
chmod +x setup.sh
./setup.sh
```
Este script:

- Actualiza el sistema
- Instala OpenJDK 17
- Instala Apache
- Instala Certbot
- Habilita módulos necesarios (proxy, proxy_http, rewrite, ssl)
- Reinicia Apache

### Construir las aplicaciones
```
./gradlew clean build
```
Ejecutar las aplicaciones:
```
java -jar app1/build/libs/app1.jar &
java -jar app2/build/libs/app2.jar &
```

### Configuración de Apache
La configuración de Apache utilizada en esta práctica se encuentra incluida en el repositorio dentro del directorio correspondiente.
Ejemplo de configuracion:
```
<VirtualHost *:80>
    ServerName app1.sstudio.me

    ProxyPreserveHost On
    ProxyPass / http://localhost:7000/
    ProxyPassReverse / http://localhost:7000/
</VirtualHost>
```
