# Task Manager API

A REST API for managing users and tasks built with **Java and Spring
Boot**.

This project is part of my backend development portfolio and focuses on
building a **clean, testable and maintainable REST API** using modern
backend practices such as layered architecture, proper testing and
containerized infrastructure.

The goal of this project is to demonstrate backend development skills
including:

-   REST API design
-   layered architecture
-   database integration
-   testing strategies
-   environment configuration

------------------------------------------------------------------------

# Tech Stack

-   **Java**
-   **Spring Boot**
-   **Spring Web**
-   **Spring Data JPA**
-   **PostgreSQL**
-   **Docker**
-   **H2 (tests)**
-   **JUnit 5**
-   **Mockito**
-   **MockMvc**
-   **Maven**

------------------------------------------------------------------------

# Architecture

The application follows a **layered architecture** to ensure separation
of concerns and maintainability.

    Controller → Service → Repository → Database

## Layers

### Controller

-   Handles HTTP requests and responses
-   Performs request validation
-   Delegates operations to the service layer

### Service

-   Contains the business logic
-   Coordinates operations between repositories
-   Enforces application rules

### Repository

-   Handles database access
-   Uses Spring Data JPA for persistence

### Model

-   Contains the domain entities (`User`, `Task`)

### Exception

-   Global exception handling
-   Custom domain exceptions

------------------------------------------------------------------------

# Features

-   Create users
-   Retrieve users
-   Create tasks
-   Assign tasks to users
-   Update task title
-   Delete tasks
-   Mark tasks as completed
-   Input validation
-   Global exception handling
-   Layered architecture
-   Unit and integration testing

------------------------------------------------------------------------

# Project Structure

    src/main/java/com/cristiancid/taskmanager

    controller     → REST controllers
    service        → Business logic
    repository     → Data access layer
    model          → JPA entities
    dto            → Request DTOs
    exception      → Global exception handling

------------------------------------------------------------------------

# Infrastructure

The development database runs inside a **PostgreSQL Docker container**.

Using Docker provides:

-   a reproducible development environment
-   no need to install PostgreSQL locally
-   easy setup for anyone cloning the project

------------------------------------------------------------------------

# Running the Project

## 1 Start PostgreSQL

From the project root:

``` bash
docker compose up -d
```

This will start the PostgreSQL container used for development.

------------------------------------------------------------------------

## 2 Run the application

``` bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The API will start at:

    http://localhost:8080

------------------------------------------------------------------------

# Database

The project uses **different databases depending on the environment**.

### Development

-   PostgreSQL
-   Runs inside Docker
-   Persistent database

### Testing

-   H2 in-memory database
-   Fast and isolated test environment

------------------------------------------------------------------------

# Testing

The project includes multiple layers of testing.

### Service Tests

-   Test business logic
-   Use **JUnit 5 + Mockito**

### Controller Tests

-   Test REST endpoints
-   Use **Spring MockMvc**

### Repository Tests

-   Test JPA repositories
-   Use **@DataJpaTest**

Run tests with:

``` bash
./mvnw test
```

------------------------------------------------------------------------

# Example Endpoints

### Create User

    POST /users

### Get User

    GET /users/{id}

### Create Task

    POST /users/{userId}/tasks

### Get Tasks by User

    GET /users/{userId}/tasks

### Update Task Title

    PUT /tasks/{id}

### Complete Task

    PATCH /tasks/{id}/complete

### Delete Task

    DELETE /tasks/{id}

------------------------------------------------------------------------

# Project Status

This project is **actively being improved** as part of my backend
learning path.

Planned improvements include:

-   database migrations with Flyway
-   pagination
-   authentication with Spring Security
-   improved API documentation

------------------------------------------------------------------------

# Author

Cristian Cid

GitHub\
https://github.com/cristiancid-dev