# Fase de construcción
FROM maven:3.8.1-openjdk-17 AS build

# Copiar todo el contenido del proyecto al contenedor
COPY . /app

# Establecer el directorio de trabajo
WORKDIR /app

# Descargar las dependencias y luego compilar el proyecto
RUN ./mvnw clean package -DskipTests

# Fase de ejecución
FROM openjdk:17-jdk-slim

# Exponer el puerto 8080
EXPOSE 8080

# Copiar el archivo JAR construido en la fase de build al contenedor
COPY --from=build /app/target/msvc-users-0.0.1-SNAPSHOT.jar app.jar

# Definir el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]