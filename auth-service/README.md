# 🔐 Auth Service - Task Management Platform

Auth Service is a microservice responsible for user authentication and authorization in the Task Management Platform. It provides secure user registration, login, JWT-based authentication, and token management.

## 🚀 Features

- **User Registration** - Securely register users with encrypted passwords.
- **JWT Authentication** - Issue access and refresh tokens.
- **Login & Logout** - Authenticate users and invalidate sessions.
- **User Information** - Retrieve the current authenticated user's data.
- **Role-Based Access Control (RBAC)** - Assign roles and restrict access to certain APIs.

## 🛠 Technologies Used

- **Java 17**
- **Spring Boot 3** (Spring Security, Spring Data JPA)
- **PostgreSQL**
- **JWT (JSON Web Token)**
- **Gradle**
- **Docker & Docker Compose**

## 📂 API Endpoints

| HTTP Method | Endpoint         | Description |
|------------|-----------------|-------------|
| **POST**   | `/auth/register` | Register a new user |
| **POST**   | `/auth/login`    | Authenticate and get JWT token |
| **POST**   | `/auth/refresh`  | Refresh expired JWT token |
| **POST**   | `/auth/logout`   | Invalidate refresh token |
| **GET**    | `/auth/me`       | Get authenticated user details |
| **GET**    | `/auth/validate` | Validate JWT token |

### 🔑 **Authentication Endpoints**

#### **1. Register a new user**
- **Endpoint:** `POST /auth/register`
- **Description:** Creates a new user account.
- **Request Body:**
  ```json
  {
    "username": "user1",
    "password": "securepassword"
  }
  ```
- **Response:**
  ```json
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "user1"
  }
  ```

#### **2. Login and get JWT token**
- **Endpoint:** `POST /auth/login`
- **Description:** Authenticates a user and returns an access token.
- **Request Body:**
  ```json
  {
    "username": "user1",
    "password": "securepassword"
  }
  ```
- **Response:**
  ```json
  {
    "token": "<JWT_ACCESS_TOKEN>",
    "refreshToken": "<JWT_REFRESH_TOKEN>"
  }
  ```

#### **3. Refresh expired JWT token**
- **Endpoint:** `POST /auth/refresh`
- **Description:** Generates a new access token using a refresh token.
- **Request Body:**
  ```json
  {
    "refreshToken": "<JWT_REFRESH_TOKEN>"
  }
  ```
- **Response:**
  ```json
  {
    "token": "<NEW_JWT_ACCESS_TOKEN>",
    "refreshToken": "<NEW_JWT_REFRESH_TOKEN>"
  }
  ```

#### **4. Logout user**
- **Endpoint:** `POST /auth/logout`
- **Description:** Logs out the user by invalidating the refresh token.
- **Headers:**
  ```json
  {
    "Authorization": "Bearer <JWT_ACCESS_TOKEN>"
  }
  ```
- **Response:**
  ```json
  {
    "message": "User logged out successfully"
  }
  ```

### 👤 **User Endpoints**

#### **5. Get authenticated user details**
- **Endpoint:** `GET /auth/me`
- **Description:** Returns details of the currently authenticated user.
- **Headers:**
  ```json
  {
    "Authorization": "Bearer <JWT_ACCESS_TOKEN>"
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "username": "user1"
  }
  ```

#### **6. Validate JWT token**
- **Endpoint:** `GET /auth/validate`
- **Description:** Checks if the provided JWT token is still valid.
- **Headers:**
  ```json
  {
    "Authorization": "Bearer <JWT_ACCESS_TOKEN>"
  }
  ```
- **Response:**
  ```json
  {
    "valid": true
  }
  ```


## ⚙️ Configuration

### **1. Database Setup**
Ensure PostgreSQL is running with the following credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/authdb
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

### **2. Running Locally**

#### **Using Docker Compose**
```bash
docker-compose up -d
```

#### **Running with Gradle**
```bash
./gradlew bootRun
```

### **3. Testing the API**

#### **Register a new user**
```bash
curl -X POST "http://localhost:8081/auth/register" \
     -H "Content-Type: application/json" \
     -d '{"username": "admin", "password": "password123"}'
```

#### **Login and get JWT token**
```bash
curl -X POST "http://localhost:8081/auth/login" \
     -H "Content-Type: application/json" \
     -d '{"username": "admin", "password": "password123"}'
```

#### **Get user info**
```bash
curl -X GET "http://localhost:8081/auth/me" \
     -H "Authorization: Bearer <YOUR_JWT_TOKEN>"
```

## 🔧 Future Enhancements

- **OAuth2 Authentication (Google, GitHub)**
- **Two-Factor Authentication (2FA)**
- **Admin Panel for User Management**
- **API Rate Limiting & Security Enhancements**

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repo and submit a PR.

## 📄 License

This project is licensed under the MIT License.

---
Developed with ❤️ using Java & Spring Boot.