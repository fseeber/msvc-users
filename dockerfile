# Fase de construcción
FROM ubuntu:latest AS build

# Actualizar el sistema e instalar OpenJDK 17
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

# Copiar todo el contenido del proyecto al contenedor
COPY . .

# Dar permisos de ejecución al script de Maven Wrapper
RUN chmod +x mvnw
RUN chmod +x ./mvnw

# Descargar las dependencias y luego compilar el proyecto
RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

# Fase de ejecución
FROM openjdk:17-jdk-slim

# Exponer el puerto 8080
EXPOSE 8080

# Copiar el archivo JAR construido en la fase de build al contenedor
COPY --from=build target/msvc-users-0.0.1-SNAPSHOT.jar app.jar