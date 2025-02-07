# 📋 Task Management Platform

Task Management Platform is a microservices-based application designed to help teams and individuals efficiently manage tasks and projects. Built with **Java (Spring Boot)**, **PostgreSQL**, and **Docker**, this platform provides flexibility, scalability, and robust performance.

## 🚀 Features

- **User Authentication:** Secure registration and login with JWT-based authentication.
- **Task Management:** Create, update, delete, and organize tasks with deadlines and priorities.
- **Project Boards:** Kanban-style boards for visualizing task progress (To Do, In Progress, Done).
- **Team Collaboration:** Assign tasks to team members and manage project roles.
- **Notifications:** Real-time updates and email notifications for important task changes.

## 🧩 Microservices Architecture

- **API Gateway:** Central entry point for routing requests to microservices.
- **Auth Service:** Handles user authentication and JWT token management.
- **User Service:** Manages user profiles and account settings.
- **Task Service:** CRUD operations for tasks.
- **Project Service:** Manages projects, boards, and team collaborations.
- **Notification Service:** Sends email and real-time notifications (optional).

## 📊 Evolutionary Architecture and Fitness Functions

This project also serves as a practical implementation for research related to **evolutionary architecture** and **fitness functions** in software engineering. The platform allows:

- **Evaluation of Quality Criteria:** Using automated fitness functions to measure and compare system performance across different versions.
- **Sensitivity Analysis:** Observing how architectural changes impact system indicators to ensure continuous improvement.
- **Experimental Validation:** Testing the effectiveness of fitness functions in real-world scenarios, making them sensitive enough to detect significant architectural shifts.

## 🗂️ Technologies Used

- **Backend:** Java 17, Spring Boot, Spring Cloud, Spring Security, Spring Data JPA
- **Database:** PostgreSQL
- **Frontend:** React (or Angular)
- **Messaging:** RabbitMQ (for notifications)
- **Containerization:** Docker, Docker Compose
- **CI/CD:** GitHub Actions

## ⚙️ How to Run Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/task-management-platform.git
   cd task-management-platform
   ```

2. **Run all services with Docker Compose:**
   ```bash
   docker-compose up --build
   ```

3. **Access the application:**
    - API Gateway: [http://localhost:8080](http://localhost:8080)
    - Frontend: [http://localhost:3000](http://localhost:3000) (if applicable)

## 📦 Project Structure

```bash
task-management-platform/
├── api-gateway/               # API Gateway for routing requests
├── auth-service/              # Authentication service (JWT)
├── user-service/              # User profile management
├── task-service/              # Task CRUD operations
├── project-service/           # Project and board management
├── notification-service/      # Notification handling (optional)
├── frontend/                  # User interface (React or Angular)
├── docker-compose.yml         # Orchestrates services with Docker
└── README.md                  # Project documentation
```

## ✅ Future Improvements

- Real-time WebSocket notifications
- Advanced role-based access control
- Integration with external calendar APIs
- Dark mode support in UI
- Enhanced fitness function metrics for architectural analysis

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## 📄 License

This project is licensed under the MIT License.

---

Developed with ❤️ using Java & Spring Boot.

