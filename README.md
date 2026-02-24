# Todo API 🚀

**Todo API** built with **Spring Boot 4.x + JPA + PostgreSQL + OpenAPI/Swagger**

Todo API now features JWT authentication, separate public/admin endpoints, and auto-generated Swagger documentation.

## ✨ Features

- ✅ **CRUD operations** for Todos & Users with DTOs
- 🗄 **PostgreSQL** with JPA/Hibernate auto schema generation
- 🔐 **JWT Bearer authentication** (login/signup required)
- 📱 **RESTful API** with security tags (Public/Admin/User APIs)
- 📖 **Swagger UI** for interactive API documentation & testing
- ✅ **Health check** endpoint for monitoring
- ⚠️ **Global exception handling** for clean error responses
- ⚡ **Maven wrapper** (no local Maven installation needed)

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

1. **Copy environment template:**
   ```bash
   cp .env.example .env
   ```

2. **Update `.env` with your database details:**
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/todos
   DB_USERNAME=postgres
   DB_PASSWORD=your_secure_password
   ```

**Database tables (`todos`, `users`) are auto-created** via `spring.jpa.hibernate.ddl-auto=update`.

## 📁 Project Structure

```
Todo/
├── src/main/java/com/minal/todo/
│   ├── TodoApplication.java              # Main Spring Boot app
│   ├── config/
│   │   └── SecurityConfig.java           # JWT Security configuration
│   ├── controllers/                      # REST API Controllers
│   │   ├── PublicController.java         # Public endpoints (/signup, /login)
│   │   ├── UserController.java           # User management endpoints
│   │   └── TodoController.java           # Todo CRUD operations
│   ├── dto/                              # Data Transfer Objects
│   │   ├── TodoRequestDTO.java
│   │   ├── TodoUpdateDTO.java
│   │   ├── UserRequestDTO.java
│   │   └── UserUpdateDTO.java
│   ├── exception/                        # Custom exceptions & handler
│   │   ├── GlobalExceptionHandler.java
│   │   ├── IncorrectPasswordException.java
│   │   └── UserNotFoundException.java
│   ├── filter/
│   │   └── JwtRequestFilter.java         # JWT token validation filter
│   ├── models/                           # JPA Entity classes
│   │   ├── TodoModel.java
│   │   └── UserModel.java
│   └── services/                         # Business logic
│       ├── TodoServiceImpl.java
│       └── UserServiceImpl.java
├── src/main/resources/
│   └── application.properties            # App configuration
├── .env.example                          # ✅ COMMIT - Environment template
├── .env                                  # ❌ .gitignore - Local secrets
├── pom.xml                               # Dependencies (Lombok + springdoc-openapi)
├── mvnw & mvnw.cmd                       # Maven wrapper
└── README.md
```

## 📖 Swagger Documentation

**Interactive API documentation** is available at runtime:

1. **Swagger UI (Recommended):**
   ```
   http://localhost:8080/swagger-ui.html
   or
   http://localhost:8080/swagger-ui/index.html
   ```

2. **Raw OpenAPI JSON:**
   ```
   http://localhost:8080/v3/api-docs
   ```

3. **Using Swagger UI:**
   - Test **Public APIs** first (`/signup`, `/login`, `/health-check`)
   - Create account → Get **JWT Bearer token**
   - Click **"Authorize"** button → Enter `Bearer <your-jwt-token>`
   - Test **protected endpoints** (Admin/User APIs)

**Pro Tip:** All endpoints are tagged (Public/Admin/User) and grouped in Swagger UI.

## 🔐 Authentication Flow

```
1. POST /signup              → Create account
   {"userName": "john", "password": "secret123"}

2. POST /login               → Get JWT token
   {"userName": "john", "password": "secret123"}
   ↓ Returns: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

3. Use token in Authorization header:
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🧪 Health Check

**App monitoring endpoint:**
```
GET http://localhost:8080/health-check
```

## 🧪 Development Commands

```bash
# Clean compile & run tests
./mvnw clean compile test

# Build executable JAR
./mvnw clean package

# Run JAR directly
java -jar target/*.jar

# Development server (hot reload)
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"

# Generate Swagger docs only
./mvnw springdoc-openapi:generate
```

## 🔍 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Port 8080 busy** | Kill process: `lsof -ti:8080 \| xargs kill -9` |
| **DB connection failed** | Check PostgreSQL running & `.env` credentials |
| **Swagger 404** | Wait for app startup (30-60s) or check logs |
| **JWT 401** | Verify token not expired, correct `Bearer ` prefix |

## 📋 API Tags Overview

- **Public APIs** - No auth required (`/signup`, `/login`, `/health-check`)
- **Todo APIs** - Auth required (CRUD operations on `/todos`)
- **User APIs** - Auth required (user management on `/users`)
- **Admin APIs** - Auth + Admin role (`/users/get-all`)

**Full endpoint details → See Swagger UI!** 🎉