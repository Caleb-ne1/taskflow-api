# TaskFlow API
A RESTful API for task management with user authentication and authorization built with Spring Boot.

## Features
- **User Authentication & Authorization**: Secure JWT-based authentication with HTTP-only cookies

- **Task Management**: Create, read, update and delete tasks

- **User Profile Management**: View profile, update password, delete account

- **Security**: Password encryption, protected endpoints, token validation

- **Validation**: Request validation with meaningful error messages

## Tech Stack
* Java 21
* Spring Boot 3.x
* Spring Security
* JWT (JSON Web Tokens)
* Spring Data JPA
* BCrypt Password Encoding
* Maven

## API Endpoints
### Authentication
| Method	 | Endpoint	          | Description	                  | Request Body	             | Response                           |
|---------|--------------------|-------------------------------|---------------------------|------------------------------------|
| POST	   | /api/auth/register | 	Register a new user          | 	UserRegistrationRequest	 | UserRegistrationResponse           |
| POST	   | /api/auth/login	   | Login and receive JWT cookie	 | AuthRequest	              | Success message + HTTP-only cookie |
| POST	   | /api/auth/logout	  | Logout and clear cookie       | 	-	                       | Success message                    |

### Tasks
| Method	 | Endpoint	    | Description	                         | Request Body	 | Response             |
|---------|--------------|--------------------------------------|---------------|----------------------|
| GET	    | /	           | API health check	                    | -	            | Status message       |
| POST	   | /tasks	      | Create a new task	                   | TaskRequest	  | TaskResponse         |
| GET	    | /tasks	      | Get all tasks for authenticated user | 	-	           | List of TaskResponse |
| GET	    | /tasks/{id}  | 	Get a specific task by ID           | 	-	           | TaskResponse         |
| PUT	    | /tasks/{id}	 | Update a task	                       | TaskRequest	  | TaskResponse         |
 | DELETE  | /tasks/{id}  | delete a task by ID                  | -             | ApiResponse          |
### User Management
| Method	 | Endpoint	                  | Description	          | Request Body	            | Response     |
|---------|----------------------------|-----------------------|--------------------------|--------------|
| GET	    | /api/users/profile         | 	Get user profile     | 	-	                      | UserResponse |
| PUT	    | /api/users/update-password | 	Update user password | 	  PasswordResetRequest	 | ApiResponse  |
| DELETE	 | /api/users/delete          | 	Delete user account  | 	-	                      | ApiResponse  |

## Request/Response Models
### Authentication
### UserRegistrationRequest

```json
{
"email": "user@example.com",
"password": "securepassword"
}
```

### UserRegistrationResponse

```json
{
"id": 1,
"email": "user@example.com",
"message": "User registered successfully"
}
```

### AuthRequest

```json
{
"email": "user@example.com",
"password": "securepassword"
}
```

## Tasks
### TaskRequest

```json
{
"title": "Complete project documentation",
"description": "Write comprehensive API documentation",
"completed": false
}
```

### TaskResponse

```json
{
"id": 1,
"title": "Complete project documentation",
"description": "Write comprehensive API documentation",
"completed": false
}
```

## User Management
### UserResponse

```json
{
"id": 1,
"email": "user@example.com",
"password": "hashed_password"
}
```

### PasswordResetRequest

```json
{
"currentPassword": "oldpassword",
"newPassword": "newsecurepassword"
}
```

### ApiResponse

```json
{
"status": 200,
"message": "Password updated successfully",
"timestamp": "2024-01-01T12:00:00Z",
"data": null
}
```

## Security
* JWT tokens stored in HTTP-only cookies for enhanced security
* Passwords encrypted using BCrypt
* Protected endpoints require valid JWT token
* Task ownership verification prevents unauthorized access
* Session management with secure logout

## Setup and Installation
### Prerequisites
* Java 21
* Maven
* MySQL

### Configuration
1. Clone the repository

```bash
git clone https://github.com/Caleb-ne1/taskflow-api.git
cd taskflow-api
```

2. Configure application.properties or application.yml

```properties
# Database Configuration
spring.datasource.url=jdbc:mariadb://localhost:3306/taskflow_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# cors origin configuration
app.cors.allowed-origins=http://localhost:5173,https://example.com

# jwt secret key configuration
app.jwt.secret=secret_key_at_least_32_character_long

# Server Configuration
server.port=8080

```

3. Build the project

```bash
mvn clean install
```

4. Run the application

```bash
mvn spring-boot:run
```

The API will be available at http://localhost:8080