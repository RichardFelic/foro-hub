# Foro Hub - API REST

![Badge-Spring](https://github.com/user-attachments/assets/6be9d3d2-dae7-464e-9fc3-365d13fcdcc0)

---

## Project Description

Foro Hub is an application designed to manage online forums, facilitating user interaction through topics and responses. The project was developed using **Spring Boot 3.4.1** and **Java 21**, leveraging the latest features and enhancements of these technologies.

### Key Features:

- **Topic Management:** Create, edit, delete, and list topics.
- **Response Management:** Add and manage responses associated with topics.
- **User Management:** Administer users and their interactions with topics and responses.
- **Authentication and Authorization:** Implemented security with Spring Security and JWT.
- **API Documentation:** Automatically generated with Springdoc OpenAPI.

---

## Project Requirements

### Main Dependencies:

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

## API Endpoints

### **Admin**

#### User Controller

- `GET /api/v1/user/{id}`: Get a user by ID.
- `PUT /api/v1/user/{id}`: Update a user.
- `DELETE /api/v1/user/{id}`: Delete a user.
- `PUT /api/v1/user/activate/{id}`: Activate a user.
- `GET /api/v1/user`: List all users.
- `POST /api/v1/user`: Create a new user.

#### Topic Controller

- `GET /api/v1/topic/{id}`: Get a topic by ID.
- `PUT /api/v1/topic/{id}`: Update a topic.
- `DELETE /api/v1/topic/{id}`: Delete a topic.
- `PUT /api/v1/topic/activate/{id}`: Activate a topic.
- `GET /api/v1/topic`: List all topics.
- `POST /api/v1/topic`: Create a new topic.

#### Response Controller

- `GET /api/v1/response/{id}`: Get a response by ID.
- `PUT /api/v1/response/{id}`: Update a response.
- `DELETE /api/v1/response/{id}`: Delete a response.
- `PUT /api/v1/response/active/{id}`: Activate a response.
- `GET /api/v1/response`: List all responses.
- `POST /api/v1/response`: Create a new response.

### **User (Public)**

#### Public Topic Controller

- `GET /api/v1/public/topic/{id}`: View a topic by ID.
- `PUT /api/v1/public/topic/{id}`: Update a topic.
- `DELETE /api/v1/public/topic/{id}`: Delete a topic.
- `GET /api/v1/public/topic`: List all topics.
- `POST /api/v1/public/topic`: Create a new topic.

#### Public Response Controller

- `GET /api/v1/public/response/{id}`: View a response by ID.
- `PUT /api/v1/public/response/{id}`: Update a response.
- `DELETE /api/v1/public/response/{id}`: Delete a response.
- `GET /api/v1/public/response`: List all responses.
- `POST /api/v1/public/response`: Create a new response.

#### Public User Controller

- `GET /api/v1/public/user`: List public users.
- `GET /api/v1/public/user/{id}`: Get details of a public user.

### **Authentication**

#### Auth Controller

- `POST /api/v1/auth/register`: Register a user.
- `POST /api/v1/auth/refresh`: Refresh a JWT token.
- `POST /api/v1/auth/logout`: Log out.
- `POST /api/v1/auth/login`: Log in.

---

## Installation

1. Clone the repository:
   ```bash
   git clone <REPOSITORY_URL>
   ```
2. Configure the database in the `application.properties` or `application.yml` file.
3. Run migrations using Flyway.
4. Start the application:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## Contributions

Contributions are welcome. If you want to contribute, please open an issue or a pull request.

---

## Contact

For more information, you can reach us at [felricharde42@gmail.com](mailto:felricharde42@gmail.com).

