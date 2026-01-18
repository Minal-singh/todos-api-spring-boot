# Todo API 🚀

**Todo API** built with **Spring Boot 4.x + JPA + PostgreSQL**

## ✨ Features

- ✅ **CRUD operations** for Todos & Users
- 🗄 **PostgreSQL** with JPA/Hibernate
- 🔐 **Secure environment configuration**
- 🧪 **Maven wrapper** (no local Maven needed)
- 📱 **RESTful API** endpoints
- ⚡ **Auto schema generation**

## 🚀 Quick Start

```bash
# Clone the project
git clone <your-repo-url>
cd Todo

# 1. Setup environment (copy template)
cp .env.example .env
# Edit .env with your PostgreSQL credentials

# 2. Start the application
./mvnw spring-boot:run
```

**✅ App will be live at:** `http://localhost:8080`

## 🛠 Prerequisites

| Requirement | Version | Notes |
|-------------|---------|-------|
| **Java** | 17+ | OpenJDK recommended |
| **PostgreSQL** | 13+ | Localhost:5432 |
| **Maven** | ✅ Included (`./mvnw`) | No installation needed |

## 📦 Environment Setup

1. **Copy template:**
   ```bash
   cp .env.example .env
   ```

2. **Update `.env`:**
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/todos
   DB_USERNAME=postgres
   DB_PASSWORD=your_secure_password
   ```

## 🗄 Database Setup

**Automatic!** No manual SQL needed:

```properties
# application.properties
spring.jpa.hibernate.ddl-auto=update
```

**Tables auto-created:**
- `todos` (title, completed, user_id)
- `users` (username, password)

## 📁 Project Structure

```
Todo/
├── src/main/java/com/minal/todo/
│   ├── models/      # @Entity classes
│   │   ├── TodoModel.java
│   │   └── UserModel.java
│   ├── services/    # Business logic
│   ├── controllers/ # REST API
│   └── TodoApplication.java
├── src/main/resources/
│   └── application.properties
├── .env.example    # ✅ COMMIT
├── .env            # ❌ .gitignore
├── pom.xml         # Dependencies + Lombok
└── README.md
```

## 🌐 API Endpoints
| Method | Endpoint         | Description                |
| ------ | ---------------- |----------------------------|
| GET    | /users           | List all users             |
| GET    | /users/user1     | Get user by username       |
| POST   | /users           | Create new user            |
| GET    | /user1/todos      | Get todos for given user   |
| GET    | /user1/todos/{id} | Get specific todo          |
| POST   | /user1/todos      | Create todo for given user |
| PUT    | /user1/todos/{id} | Update todo                |
| DELETE | /user1/todos/{id} | Delete todo                |

**Example:**
# Create user
curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d '{"userName":"john","password":"secret123"}'

# Create todo for user
curl -X POST http://localhost:8080/john/todos \
-H "Content-Type: application/json" \
-d '{"title":"Learn Spring Boot","completed":false}'

# List user todos
curl http://localhost:8080/john/todos


## 🧪 Development Commands

```bash
# Compile & Test
./mvnw clean compile test

# Build JAR
./mvnw clean package

# Run JAR
java -jar target/*.jar

# Dev server with hot reload
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
```