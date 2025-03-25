# Usar una imagen base de PHP con Composer
FROM php:8.2-cli

# Instalar dependencias necesarias
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Instalar la extensión sockets de PHP
RUN docker-php-ext-install sockets

# Instalar Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Crear un directorio para la aplicación
WORKDIR /app

# Copiar los archivos de la aplicación
COPY Publisher2.php /app/Publisher2.php
COPY composer.json /app/composer.json

# Instalar dependencias de Composer
RUN composer install --no-dev --optimize-autoloader --ignore-platform-req=ext-sockets

# Comando para ejecutar el publisher
CMD ["php", "Publisher2.php"]
