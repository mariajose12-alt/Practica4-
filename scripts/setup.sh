#!/bin/bash

echo "Actualizando sistema..."
sudo apt update

echo "Instalando dependencias..."
sudo apt install -y openjdk-17-jdk apache2 certbot python3-certbot-apache

echo "Habilitando módulos Apache..."
sudo a2enmod proxy proxy_http rewrite ssl

sudo systemctl restart apache2

echo "Entorno listo."

