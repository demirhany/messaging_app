# Messaging Application

## Overview
This is a messaging application built using **Spring Boot** with authentication and authorization. It enables users to send and receive messages through **Spring Security** and **JWT-based authentication**. The application uses **PostgreSQL** as its database.

## Features
- Built with **Spring Boot** for a robust backend.
- Implements **Spring Security** for user authentication and authorization.
- Uses **JWT (JSON Web Token)** for secure access control.
- Stores messages and user information in **PostgreSQL**.
- **WebSocket is not yet implemented**, but future support is planned.

## Tech Stack
- **Spring Boot** - Backend framework
- **Spring Security** - Authentication & authorization
- **JWT** - Secure token-based authentication
- **PostgreSQL** - Relational database management
- **JPA (Hibernate)** - ORM for database operations

## Installation
### Prerequisites
- **Java 21**
- **Maven**
- **Docker** (Ensure Docker is installed and running)

### Steps to Run
1. **Clone the repository:**
   ```sh
   git clone https://github.com/demirhany/messaging_app
   cd messaging_app
   ```
2. **Configure the database in `application.properties`:**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/messaging_app
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password 
   spring.jpa.hibernate.ddl-auto=update
   ```
3. **Configure password(optional, default it is `1234`) of `docker-compose.yml` and run it:**
   ```sh
   cd docker
   sudo docker compose up
   ```
4. **Create database in the docker container:**
   ```sh
   sudo docker exec -it postgresdb-messagingApp psql -U postgres -c "CREATE DATABASE messaging_app;"
   ```
5. **Build the project:**
   ```sh
   mvn clean install
   ```
6. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints
### Authentication
- **`POST /api/authentication/register`** - Register a new user
- **`POST /api/authentication/login`** - Authenticate and receive a JWT token

### Messaging
- **`POST /api/message/send`** - Send a message (authenticated users only)
- **`GET /api/message/get`** - Retrieve messages (authenticated users only)

## Future Improvements
- **WebSocket implementation** for real-time messaging.
- **Message encryption** for added security.

## License
This project is licensed under the **MIT License**.

