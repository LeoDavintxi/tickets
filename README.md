
# Prueba de la aplicación en local

## Descripción

Este proyecto es una API REST desarrollada en Java utilizando Spring Boot, que permite gestionar tickets. Puedes crear, leer, actualizar y eliminar tickets en una base de datos PostgreSQL. A continuación, se detalla cómo ejecutar este proyecto en tu entorno local.

## Requisitos previos

Antes de ejecutar este proyecto en tu máquina local, asegúrate de tener instalados los siguientes programas:

1. **Java 17 o superior**: Puedes verificar si tienes Java instalado ejecutando `java -version` en la terminal. Si no lo tienes, puedes descargarlo desde [aquí](https://adoptopenjdk.net/).
2. **Docker**: Usaremos Docker para crear un contenedor de la base de datos PostgreSQL. Puedes instalar Docker desde [aquí](https://www.docker.com/products/docker-desktop).
3. **Maven**: Para la gestión de dependencias y la construcción del proyecto. Puedes instalar Maven desde [aquí](https://maven.apache.org/install.html).
4. **Postman** (opcional): Para realizar las pruebas a los endpoints de la API. Puedes instalar Postman desde [aquí](https://www.postman.com/downloads/).

## Pasos para ejecutar el proyecto localmente

### 1. Clonar el repositorio

Si aún no tienes el proyecto, clónalo utilizando Git:

```bash
git clone https://github.com/LeoDavintxi/tickets.git
cd tickets
```

### 2. Configurar la base de datos con Docker

Este proyecto utiliza una base de datos PostgreSQL. Para configurarla localmente, utilizaremos Docker.

#### 2.1 Crear el contenedor de la base de datos

Crea un archivo `docker-compose.yml` en la raíz del proyecto (si aún no tienes uno) con el siguiente contenido:

```yaml
version: '3.8'
services:
  db:
    image: postgres:latest
    container_name: ticket_db
    environment:
      POSTGRES_DB: tickets_db
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
    ports:
      - "5432:5432"
    networks:
      - backend
networks:
  backend:
    driver: bridge
```

#### 2.2 Levantar el contenedor

Con el archivo `docker-compose.yml` creado, ejecuta el siguiente comando para iniciar el contenedor de PostgreSQL:

```bash
docker-compose up -d
```

Este comando descargará la imagen de PostgreSQL (si no la tienes) y levantará un contenedor con la base de datos accesible en el puerto 5432.

### 3. Configurar las propiedades de la base de datos

Abre el archivo `src/main/resources/application.properties` y configura la conexión a la base de datos PostgreSQL. Agrega lo siguiente:

```properties
spring.application.name=tickets
spring.datasource.url=jdbc:postgresql://localhost:5432/appTickets
spring.datasource.username=appUser
spring.datasource.password=appTicketsPassword2024
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.security.user.name=user
spring.security.user.password=pass
```

Esto configurará la conexión con la base de datos que corre en Docker.

### 4. Compilar y ejecutar la aplicación

Para compilar y ejecutar la aplicación, abre la terminal en el directorio raíz del proyecto y ejecuta los siguientes comandos:

```bash
mvn clean install
mvn spring-boot:run
```

Esto compilará el proyecto y lo ejecutará. Si todo está bien configurado, la aplicación estará disponible en `http://localhost:8080`.

### 5. Probar los endpoints

Una vez que la aplicación esté corriendo, puedes probar los endpoints utilizando herramientas como Postman o `curl`. Los endpoints disponibles son:

- **GET /api/v1/ticket**: Obtener todos los tickets.
- **GET /api/v1/ticket/{id}**: Obtener un ticket por su ID.
- **POST /api/v1/ticket**: Crear un nuevo ticket.
- **DELETE /api/v1/ticket/{id}**: Eliminar un ticket por su ID.
- **PATCH /api/v1/ticket/{id}**: Actualizar un ticket por su ID.

#### Ejemplo de uso con `curl`:

1. **Obtener todos los tickets**:
   ```bash
   curl -X GET http://localhost:8080/api/v1/ticket
   ```

2. **Crear un nuevo ticket**:
   ```bash
   curl -X POST http://localhost:8080/api/v1/ticket -H "Content-Type: application/json" -d '{"titulo": "Nuevo Ticket", "descripcion": "Descripción del ticket"}'
   ```

3. **Eliminar un ticket**:
   ```bash
   curl -X DELETE http://localhost:8080/api/v1/ticket/{id}
   ```

4. **Actualizar un ticket**:
   ```bash
   curl -X PATCH http://localhost:8080/api/v1/ticket/{id} -H "Content-Type: application/json" -d '{"titulo": "Ticket Actualizado", "descripcion": "Descripción actualizada"}'
   ```

### 6. Detener el contenedor de Docker

Si deseas detener el contenedor de la base de datos cuando hayas terminado de usarlo, puedes ejecutar el siguiente comando:

```bash
docker-compose down
```

Esto detendrá y eliminará el contenedor de la base de datos.

## Detalles adicionales

### Docker para la base de datos

El contenedor de Docker se encargará de crear la base de datos `tickets_db` automáticamente si no existe. Si necesitas eliminar o reiniciar el contenedor, puedes ejecutar los siguientes comandos:

```bash
docker-compose down
docker-compose up -d
```

### Volúmenes de Docker

Si deseas que los datos de la base de datos se mantengan incluso después de detener el contenedor, asegúrate de configurar volúmenes en el archivo `docker-compose.yml` para persistir los datos:

```yaml
volumes:
  db_data:
```

### Errores comunes

1. **Error de conexión a la base de datos**: Asegúrate de que Docker esté corriendo y que el contenedor de la base de datos esté activo.
2. **Puerto 5432 ya está en uso**: Si tienes otro servicio corriendo en el puerto 5432, puedes cambiar el puerto en el archivo `docker-compose.yml`.

---

¡Listo! Ahora puedes ejecutar la aplicación localmente, probar los endpoints y desarrollar nuevas funcionalidades.