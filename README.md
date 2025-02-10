# **Microservicio de Usuarios (msvc-users)**

Este proyecto es un microservicio para la gestión de usuarios en una arquitectura de microservicios, desarrollado en **Java 17** utilizando **Spring Boot 3.3.1**.

## **Características**
- CRUD de usuarios.
- Base de datos relacional (H2 en memoria, configurable a otra BD).
- Documentación con Swagger (OpenAPI).
- Construcción y despliegue en contenedores Docker.  

---

## **Requisitos**
- Java 17+
- Maven 3.6+
- Docker

---

## **Instalación y Ejecución Local**
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/msvc-users.git
   cd msvc-users

2. Compilar y empaquetar el proyecto:
   ```bash
   ./mvnw clean package -DskipTests
3. Ejecutar la aplicación:
   ```bash
   java -jar target/msvc-users-0.0.1-SNAPSHOT.jar

La aplicación estará disponible en http://localhost:8080.

## **Ejecución con Docker**
Construcción de la imagen y ejecución
  ```bash
  docker build -t msvc-users .
  docker run -p 8080:8080 msvc-users


