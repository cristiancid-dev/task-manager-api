# Task Manager API

A REST API for managing users and tasks built with Java and Spring Boot.

This project is part of my backend development portfolio and focuses on
building a clean, testable and maintainable REST API using modern Java
backend practices and layered architecture.

------------------------------------------------------------------------

## Tech Stack

-   Java
-   Spring Boot
-   Spring Web
-   Spring Data JPA
-   H2 Database
-   Maven
-   JUnit

------------------------------------------------------------------------

## Architecture

The application follows a layered architecture to ensure separation of
concerns and maintainability.

Controller → Service → Repository → Database

### Layers

**Controller** - Handles HTTP requests and responses - Validates
incoming request data - Calls the appropriate service methods

**Service** - Contains the business logic - Coordinates operations
between repositories - Applies application rules

**Repository** - Handles database access - Uses Spring Data JPA for
persistence

**Model** - Contains the application entities (User, Task)

------------------------------------------------------------------------

## Features

-   Create users
-   Retrieve users
-   Create tasks
-   Assign tasks to users
-   Update tasks
-   Delete tasks
-   Task status management
-   Input validation
-   Exception handling
-   Unit testing with JUnit

------------------------------------------------------------------------

## Project Structure

src/main/java/com/cristiancid/taskmanager

controller → REST controllers\
service → Business logic\
repository → Data access layer\
model → JPA entities\
exception → Global exception handling

------------------------------------------------------------------------

## Running the Project

Clone the repository:

git clone https://github.com/cristiancid-dev/task-manager-api.git

Navigate to the project directory:

cd task-manager-api

Run the application:

./mvnw spring-boot:run

The API will start on:

http://localhost:8080

------------------------------------------------------------------------

## Database

The project uses an H2 in-memory database for development.

H2 Console:

http://localhost:8080/h2-console

Connection settings:

JDBC URL: jdbc:h2:mem:dbtaskmanager\
User: sa\
Password: (empty)

------------------------------------------------------------------------

## Running Tests

To execute the unit tests:

./mvnw test

Tests are written using JUnit and focus on validating business logic.

------------------------------------------------------------------------

## Project Status

Work in progress.

New features and improvements will continue to be added as the project
evolves.

------------------------------------------------------------------------

## Author

Cristian Cid

GitHub:\
https://github.com/cristiancid-dev