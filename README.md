# Foro Hub - API REST

![Badge-Spring](https://github.com/user-attachments/assets/6be9d3d2-dae7-464e-9fc3-365d13fcdcc0)

Foro Hub es una aplicación diseñada para gestionar foros en línea, facilitando la interacción entre usuarios a través de tópicos y respuestas. El proyecto se desarrolló utilizando Spring Boot 3.4.1 y Java 21, aprovechando las últimas características y mejoras de estas tecnologías.
## Características principales

- **Gestión de Tópicos:** Crear, leer, actualizar y eliminar tópicos.
- **Gestión de Respuestas:** Añadir y manejar respuestas asociadas a tópicos.
- **Gestión de Usuarios:** Administrar usuarios y sus interacciones con los tópicos y respuestas.
- **Autenticación y Autorización:** Implementación de seguridad con Spring Security y JWT.
- **Documentación de la API:** Generada automáticamente con Springdoc OpenAPI.

---

## Requisitos del Proyecto

### Dependencias Principales:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>com.auth0</groupId>
        <artifactId>java-jwt</artifactId>
        <version>4.4.0</version>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.7.0</version>
    </dependency>
</dependencies>
```

---

## Endpoints de la API

### **Admin**

#### User Controller
- `GET /api/v1/user/{id}`: Obtener un usuario por ID.
- `PUT /api/v1/user/{id}`: Actualizar un usuario.
- `DELETE /api/v1/user/{id}`: Eliminar un usuario.
- `PUT /api/v1/user/activate/{id}`: Activar un usuario.
- `GET /api/v1/user`: Listar todos los usuarios.
- `POST /api/v1/user`: Crear un nuevo usuario.

#### Topic Controller
- `GET /api/v1/topic/{id}`: Obtener un tópico por ID.
- `PUT /api/v1/topic/{id}`: Actualizar un tópico.
- `DELETE /api/v1/topic/{id}`: Eliminar un tópico.
- `PUT /api/v1/topic/activate/{id}`: Activar un tópico.
- `GET /api/v1/topic`: Listar todos los tópicos.
- `POST /api/v1/topic`: Crear un nuevo tópico.

#### Response Controller
- `GET /api/v1/response/{id}`: Obtener una respuesta por ID.
- `PUT /api/v1/response/{id}`: Actualizar una respuesta.
- `DELETE /api/v1/response/{id}`: Eliminar una respuesta.
- `PUT /api/v1/response/active/{id}`: Activar una respuesta.
- `GET /api/v1/response`: Listar todas las respuestas.
- `POST /api/v1/response`: Crear una nueva respuesta.

### **User (Público)**

#### Public Topic Controller
- `GET /api/v1/public/topic/{id}`: Ver un tópico por ID.
- `PUT /api/v1/public/topic/{id}`: Actualizar un tópico.
- `DELETE /api/v1/public/topic/{id}`: Eliminar un tópico.
- `GET /api/v1/public/topic`: Listar todos los tópicos.
- `POST /api/v1/public/topic`: Crear un nuevo tópico.

#### Public Response Controller
- `GET /api/v1/public/response/{id}`: Ver una respuesta por ID.
- `PUT /api/v1/public/response/{id}`: Actualizar una respuesta.
- `DELETE /api/v1/public/response/{id}`: Eliminar una respuesta.
- `GET /api/v1/public/response`: Listar todas las respuestas.
- `POST /api/v1/public/response`: Crear una nueva respuesta.

#### Public User Controller
- `GET /api/v1/public/user`: Listar usuarios públicos.
- `GET /api/v1/public/user/{id}`: Obtener detalles de un usuario público.

### **Autenticación**

#### Auth Controller
- `POST /api/v1/auth/register`: Registrar un usuario.
- `POST /api/v1/auth/refresh`: Refrescar un token JWT.
- `POST /api/v1/auth/logout`: Cerrar sesión.
- `POST /api/v1/auth/login`: Iniciar sesión.

---

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```
2. Configurar la base de datos en el archivo `application.properties` o `application.yml`.
3. Ejecutar las migraciones con Flyway.
4. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

---

## Contribuciones
Las contribuciones son bienvenidas. Si deseas colaborar, por favor, abre un issue o un pull request.

---

## Licencia
Este proyecto está bajo la Licencia MIT. Para más detalles, consulta el archivo `LICENSE`.

---

## Contacto
Para más información, puedes contactarnos en [felricharde42@gmail.com](mailto:felricharde42@gmail.com).

