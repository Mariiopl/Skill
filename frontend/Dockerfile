# Usa una imagen base de Node.js para la construcción
FROM node:22.13.0-alpine AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos package.json y package-lock.json
COPY package*.json ./

# Instalar Angular CLI globalmente y las dependencias del proyecto
RUN npm install -g @angular/cli
RUN npm install

# Copiar el resto de los archivos del proyecto
COPY . .

# Compilar la aplicación Angular
RUN npm run build -- --configuration=production

# Usar una imagen ligera de Nginx para servir la aplicación
FROM nginx:alpine

# Eliminar los archivos predeterminados de Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copiar el archivo de configuración personalizado de Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Copiar los archivos compilados de Angular a la carpeta de Nginx
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Exponer el puerto 4200 (puerto por defecto de Angular)
EXPOSE 4200

# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]