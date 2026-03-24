# Task Manager API

A REST API for managing users and tasks built with **Java and Spring
Boot**.

This project is part of my backend development portfolio and focuses on
building a **clean, testable and maintainable REST API** using modern
backend practices such as layered architecture, automated testing and
containerized infrastructure.

The API allows creating users and managing their tasks, including
completion status, updates and pagination.

------------------------------------------------------------------------

# Tech Stack

-   **Java**
-   **Spring Boot**
-   **Spring Web**
-   **Spring Data JPA**
-   **PostgreSQL**
-   **Docker**
-   **Flyway**
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

Responsible for handling HTTP requests and responses.

-   Exposes REST endpoints
-   Validates incoming requests
-   Delegates operations to the service layer

### Service

Contains the **business logic of the application**.

-   Coordinates operations between repositories
-   Enforces application rules
-   Throws domain-specific exceptions when necessary

### Repository

Responsible for **data access**.

-   Uses Spring Data JPA
-   Provides database queries and persistence operations

### Model

Contains the **domain entities**:

-   `User`
-   `Task`

### DTO

Defines request objects used to receive API input.

### Exception

Centralized **global exception handling** and custom domain exceptions.

------------------------------------------------------------------------

# Features

-   Create users
-   Retrieve users
-   Create tasks
-   Assign tasks to users
-   Update task title
-   Delete tasks
-   Mark tasks as completed
-   Pagination when retrieving tasks
-   Input validation
-   Global exception handling
-   Layered architecture
-   Unit and integration testing
-   Database migrations with Flyway

------------------------------------------------------------------------

# Project Structure

src/main/java/com/cristiancid/taskmanager

controller → REST controllers\
service → Business logic\
repository → Data access layer\
model → JPA entities\
dto → Request DTOs\
exception → Global exception handling

------------------------------------------------------------------------

# Infrastructure

The development database runs inside a **PostgreSQL Docker container**.

Using Docker provides:

-   a reproducible development environment
-   no need to install PostgreSQL locally
-   easy setup for anyone cloning the project

Database schema changes are managed using **Flyway migrations**.

------------------------------------------------------------------------

# Running the Project

## 1 Start PostgreSQL

From the project root:

docker compose up -d

This starts the PostgreSQL container used for development.

------------------------------------------------------------------------

## 2 Run the application

./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

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

Test business logic using:

-   **JUnit 5**
-   **Mockito**

### Controller Tests

Test REST endpoints using:

-   **Spring MockMvc**

### Repository Tests

Test JPA repository behavior using:

-   **@DataJpaTest**
-   **TestEntityManager**

Run tests with:

./mvnw test

------------------------------------------------------------------------

# Example Endpoints

### Create User

POST /users

### Get User

GET /users/{id}

### Create Task

POST /users/{userId}/tasks

### Get Tasks by User (paginated)

GET /users/{userId}/tasks?page=0&size=10

### Update Task Title

PUT /tasks/{id}

### Complete Task

PATCH /tasks/{id}/complete

### Delete Task

DELETE /tasks/{id}

------------------------------------------------------------------------

# Project Purpose

This project was built to practice and demonstrate backend development
concepts such as:

-   designing REST APIs
-   implementing layered architecture
-   working with relational databases
-   writing automated tests
-   containerized development environments
-   database migrations

It serves as the **first project in a multi-project backend portfolio**.

------------------------------------------------------------------------

# Author

Cristian Cid

GitHub\
https://github.com/cristiancid-dev
