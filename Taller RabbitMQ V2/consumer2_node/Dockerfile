# Usa una imagen base de Node.js
FROM node:14

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo package.json y package-lock.json (si existe)
COPY package*.json ./

# Instala las dependencias
RUN npm install

# Copia el archivo Consumer2.js al contenedor
COPY Consumer2.js .

# Comando para ejecutar el archivo
CMD ["node", "Consumer2.js"]
