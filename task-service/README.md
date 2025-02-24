# 📌 Task Service

## 📖 Overview
`task-service` is a microservice responsible for task management. It provides **CRUD operations** for tasks, assignment functionality, and supports filtering, sorting, and status management.

## 🚀 Features
- **Create, update, delete, and retrieve tasks**
- **Assign tasks to users**
- **Task status management (`TO_DO`, `IN_PROGRESS`, `DONE`)**
- **Task prioritization and due dates**
- **Filtering and sorting support**
- **Role-based access control (`ROLE_USER`, `ROLE_ADMIN`)**

## ⚙️ Tech Stack
- **Java 17**
- **Spring Boot 3.x**
- **Spring Web** (for REST API)
- **Spring Data JPA** (for database interaction)
- **PostgreSQL** (as the database)
- **Spring Security** (for authentication & authorization)
- **Lombok** (to reduce boilerplate code)
- **Docker** (for containerization, optional)

## 📂 Project Structure
```
/task-service
│── src/main/java/com/taskmanagement/task
│   ├── api  # API controllers
│   ├── service     # Business logic
│   ├── repository  # Data access layer
│   ├── model       # Entity and DTO classes
│   ├── config      # Security & application configs
│── src/main/resources
│   ├── application.properties  # Configuration file
│── build.gradle     # Gradle dependencies
│── README.md        # Project documentation
```

## 🎯 API Endpoints
| HTTP Method | Endpoint             | Description                         | Access |
|------------|---------------------|-------------------------------------|--------|
| `POST`     | `/tasks`            | Create a new task                  | `ROLE_USER` |
| `GET`      | `/tasks/{id}`       | Get task details by ID             | `ROLE_USER` |
| `GET`      | `/tasks`            | Get all tasks (with filters)       | `ROLE_USER` |
| `PUT`      | `/tasks/{id}`       | Update an existing task            | `ROLE_USER` (owner) |
| `DELETE`   | `/tasks/{id}`       | Delete a task                      | `ROLE_ADMIN` or owner |
| `PATCH`    | `/tasks/{id}/assign`| Assign task to a user              | `ROLE_ADMIN` |

## 🛠️ Setup and Running
### 1️⃣ **Clone the repository**
```bash
git clone https://github.com/your-repo/task-service.git
cd task-service
```

### 2️⃣ **Configure the Database**
Update `application.properties` with your PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=your_user
spring.datasource.password=your_password
```

### 3️⃣ **Run the application**
```bash
./gradlew bootRun
```
Or, if using Docker:
```bash
docker-compose up -d
```

## ✅ Authentication & Authorization
- Uses **JWT-based authentication** via `auth-service`
- Roles: `ROLE_USER`, `ROLE_ADMIN`
- Users can only modify **their own tasks** unless they have `ROLE_ADMIN`

## 🧪 Testing
Run unit and integration tests with:
```bash
./gradlew test
```

## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📬 Contact
For questions or contributions, feel free to open an issue or reach out!

