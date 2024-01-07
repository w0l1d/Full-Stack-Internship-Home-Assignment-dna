## DNA Engineering Full-Stack Assignment
Build a CSV Parser.

## Table of content
- [Prerequisites](#prerequisites)

## Prerequisites
- Java 17
- Node Js v20.10.0
- Docker

## Before We begin

- Clone the repository
- run maven build `mvn -f ./backend/pom.xml clean install`
- run npm install `npm install --prefix ./frontend`

## how it works

- The application is divided into two parts, the backend and the frontend.
- The backend is a spring boot application that exposes a REST API to upload and process CSV files.
- The frontend is a Next.js application that consumes the backend's REST API and displays the results.
- The backend is configured to run on port 8080.
- The frontend is configured to run on port 3000.
- The backend is configured to auto run a postgresql docker container.
  - the docker compose will run automatically when you run the backend application.
  - the docker compose will create a database with specified name and credentials in `./backend/compose.yaml`.
  - the datasource is configured to connect to the database container.

### Important Note

* if you use **Intellij IDEA**, uncomment the following lines in `./backend/src/main/resources/application.yml`.

```yaml
spring:
  docker:
    compose:
      file: ./backend/compose.yaml
```

Intellij IDEA set the working directory `./` to the project root directory instead of `./backend`, so the compose file
will be not be found.
**it might happen with other IDEs.**

## How to run the application

1. Start the backend server. with `mvn -f ./backend/pom.xml spring-boot:run`.

2. Start the frontend server. with `npm run --prefix ./frontend dev`.
3. Open your web browser and navigate to the frontend server's address.
4. Use the application's interface to upload and process CSV files.
5. The results will be displayed in the tables.

## Description
You are invited to create a CSV parser using Java/Spring Boot, and build UI to display results using Next.js/React.


#### Interfaces

Respect the following design flow:

![Frontend interfaces](./static/interfaces.png)

- **Interface-1**: Contain an upload button.
- **Interface-2**: The Process button is added when you choose a file.
- **Interface-3**: 2 Tables showing the processing results.

**Table 1**: Employee information, displays a paginated list of employees.

**Table 2**: Jobs summary, displays for each job title, the average salary for employees.

