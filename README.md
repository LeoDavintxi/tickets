# Ticket Service Application

Este repositorio contiene una aplicación backend desarrollada en Java utilizando Spring Boot. La aplicación permite gestionar tickets con operaciones CRUD, soporte para paginación y más. Además, está preparada para ejecutarse en un entorno local utilizando Docker para la base de datos.

---

## **Requisitos Previos**

1. **Java Development Kit (JDK) 17+**
2. **Apache Maven**
3. **Docker y Docker Compose**
4. **PgAdmin (opcional, para gestionar la base de datos)**

---

## **Configuración de la Base de Datos**

Esta aplicación utiliza PostgreSQL como base de datos. Los datos de conexión son:
- **Usuario:** `appUser`
- **Contraseña:** `appTicketPassword2024`
- **Base de datos:** `appTickets`

### **Iniciar la Base de Datos con Docker**

1. Crea un archivo `docker-compose.yml` con el siguiente contenido:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    container_name: postgres_ticket_service
    environment:
      POSTGRES_USER: appUser
      POSTGRES_PASSWORD: appTicketPassword2024
      POSTGRES_DB: appTickets
    ports:
      - "5432:5432"
    volumes:
      - db_data:/var/lib/postgresql/data

  ticket_app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: ticket_service_app
    image: openjdk:17-jdk-slim
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/appTickets
      SPRING_DATASOURCE_USERNAME: appUser
      SPRING_DATASOURCE_PASSWORD: appTicketPassword2024

volumes:
  db_data:
```

2. Inicia el contenedor con:

```bash
docker-compose up -d
```

### **Conexión con PgAdmin (opcional)**

1. Accede a PgAdmin y agrega un nuevo servidor:
   - **Nombre:** `TicketServiceDB`
   - **Host:** `localhost`
   - **Puerto:** `5432`
   - **Usuario:** `appUser`
   - **Contraseña:** `appTicketPassword2024`
2. Verifica que la base de datos `appTickets` esté creada.

---

## **Configuración de la Aplicación**

1. Clona este repositorio:

```bash
git clone https://github.com/LeoDavintxi/tickets.git
cd tickets
```

2. Compila el proyecto y genera el archivo `.jar`:

```bash
mvn clean package
```

3. Crea un archivo `Dockerfile` en el directorio raíz del proyecto con el siguiente contenido:

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/ticket-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

4. Crea la imagen Docker para la aplicación:

```bash
docker build -t ticket-service-app .
```

5. Ejecuta la aplicación en un contenedor:

```bash
docker run -d -p 8080:8080 --name ticket_service_app ticket-service-app
```

---

## **Prueba de los Endpoints**

### **Iniciar el servicio**

Asegúrate de que la base de datos y la aplicación están corriendo:
- Base de datos: `docker-compose up -d`
- Aplicación: `docker run` o desde tu IDE local.

### **Endpoints Disponibles**

1. **Obtener todos los tickets (con soporte para paginación opcional)**

   - **GET** `/api/v1/ticket`
   - Parámetros opcionales:
     - `page`: Número de página.
     - `size`: Tamaño de la página.

   - **Ejemplo sin paginación:**
     ```bash
     curl -X GET http://localhost:8080/api/v1/ticket
     ```
   - **Ejemplo con paginación:**
     ```bash
     curl -X GET "http://localhost:8080/api/v1/ticket?page=0&size=5"
     ```

2. **Obtener un ticket por ID**

   - **GET** `/api/v1/ticket/{id}`
   - **Ejemplo:**
     ```bash
     curl -X GET http://localhost:8080/api/v1/ticket/1
     ```

3. **Crear un nuevo ticket**

   - **POST** `/api/v1/ticket`
   - **Cuerpo de la solicitud:**
     ```json
     {
       "title": "Nuevo Ticket",
       "description": "Descripción del ticket",
       "priority": "HIGH"
     }
     ```
   - **Ejemplo:**
     ```bash
     curl -X POST http://localhost:8080/api/v1/ticket \
     -H "Content-Type: application/json" \
     -d '{"title": "Nuevo Ticket", "description": "Descripción del ticket", "priority": "HIGH"}'
     ```

4. **Actualizar un ticket existente**

   - **PATCH** `/api/v1/ticket/{id}`
   - **Cuerpo de la solicitud:** Similar al de creación.
   - **Ejemplo:**
     ```bash
     curl -X PATCH http://localhost:8080/api/v1/ticket/1 \
     -H "Content-Type: application/json" \
     -d '{"title": "Ticket Actualizado", "priority": "LOW"}'
     ```

5. **Eliminar un ticket**

   - **DELETE** `/api/v1/ticket/{id}`
   - **Ejemplo:**
     ```bash
     curl -X DELETE http://localhost:8080/api/v1/ticket/1
     ```

---

## **Notas Adicionales**

- Si necesitas detener los contenedores:

```bash
docker-compose down
docker stop ticket_service_app
```

- Asegúrate de que los puertos `5432` (PostgreSQL) y `8080` (Aplicación) estén disponibles en tu máquina.

---

¡Listo! Ahora puedes gestionar tus tickets y explorar el servicio. 😊
