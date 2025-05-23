# Plant Doctor

Plant Doctor is a Spring Boot application designed to manage plant-related data, including botanical species, growing
locations, plants, plant comments, and seed packages.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Docker](#docker)
- [Docker Compose](#docker-compose)

## Features

- Manage botanical species
- Manage growing locations
- Manage plants and their comments
- Manage seed packages
- RESTful API for all entities
- Integration with PostgreSQL
- Flyway for database migrations
- OpenAPI documentation

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- JUnit
- Mockito
- Testcontainers
- Rest Assured
- OpenAPI

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/marcusfromsweden/plant-doctor.git
    cd plant-doctor
    ```

2. Configure the database:
    - Update the `application.properties` file with your PostgreSQL database credentials.
    - Alternatively, you can set the database URL and credentials via environment variables:
        * SPRING_DATASOURCE_PLANTDOCTOR_URL
        * SPRING_DATASOURCE_PLANTDOCTOR_USERNAME
        * SPRING_DATASOURCE_PLANTDOCTOR_PASSWORD

3. Install dependencies:
    ```sh
    mvn clean install
    ```

## Testing

To run the unit tests, use the following command:

```sh
mvn test
```

To run the integration tests, use the following command:

```sh
mvn verify -Pintegration-tests
```

**FYI**: The integration tests use TestContainers to manage the lifecycle of the PostgreSQL database during testing.

## Running the Application

To run the application, use the following command:

```sh
mvn spring-boot:run
```

To access the API, open a web browser and navigate to `http://localhost:8080/swagger-ui/index.html`.
Here you can find the OpenAPI documentation for the application and try out the API.

## Docker

You can run the application using Docker. A `Dockerfile` is provided to build the application image.

### Building the Docker Image

Before building the Docker image, you need to build the application using Maven. If you haven't, use the following
command to build the application:

```sh
mvn clean package
```

To build the Docker image, use the following command:

```sh
docker build -t plant-doctor:latest .
```

### Running the Docker Container

To run the Docker container, you must pass the environment variable SPRING_DATASOURCE_PLANTDOCTOR_URL to the container,
as there is no default value in application.properties. Use the following command:

```sh
docker run -d --name plant-doctor -p 8080:8080 -e SPRING_DATASOURCE_PLANTDOCTOR_URL plant-doctor:latest
```

## Docker Compose

A `docker-compose.yml` file is provided to launch both the PostgreSQL database and the application.

### Running with Docker Compose

To start the application and the PostgreSQL database using Docker Compose, use the following command:

```sh
docker-compose up
```

### Stopping Docker Compose

To ensure a clean state between runs, use the following command:

```sh
docker-compose down -v
```

This command will stop the containers and remove the volumes, ensuring that the next run starts with a clean state.