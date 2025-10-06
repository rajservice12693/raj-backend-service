# 🎨 Raj Jewellers Backend Service

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

A robust Spring Boot-based REST API service for managing a comprehensive jewellers inventory system, featuring jewellery items, categories, materials, user authentication, and seamless image uploads.

## 📋 Table of Contents

- [✨ Features](#-features)
- [📋 Prerequisites](#-prerequisites)
- [🗄️ Database Setup](#️-database-setup)
- [⚙️ Installation](#️-installation)
- [🔧 Configuration](#-configuration)
- [🚀 Running the Application](#-running-the-application)
- [📡 API Endpoints](#-api-endpoints)
- [🧪 Testing the API](#-testing-the-api)
- [🏗️ Project Structure](#️-project-structure)
- [🐳 Docker Setup](#-docker-setup)
- [🛠️ Technologies Used](#️-technologies-used)
- [🔧 Troubleshooting](#-troubleshooting)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)

## ✨ Features

- 🔐 **User Authentication** - Secure login system
- 💍 **Jewellery Management** - Complete CRUD operations for items
- 📂 **Category & Material Management** - Organize inventory efficiently
- ☁️ **Cloudinary Integration** - Seamless image upload and storage
- 📊 **Dashboard Analytics** - Real-time statistics and insights
- 🔗 **RESTful API** - Well-designed endpoints with consistent responses
- 🐳 **Docker Support** - Containerized deployment ready

## 📋 Prerequisites

| Requirement | Version | Description |
|-------------|---------|-------------|
| ☕ **Java** | 17+ | OpenJDK or Oracle JDK |
| 📦 **Maven** | 3.6+ | Build tool (wrapper included) |
| 🐘 **PostgreSQL** | 12+ | Database server |
| 🐳 **Docker** | Latest | For containerized deployment |
| 🐙 **Git** | Latest | Version control (optional) |

## 🗄️ Database Setup

### Local PostgreSQL Setup

1. 📥 **Install PostgreSQL** on your system
2. ▶️ **Start PostgreSQL service**
3. 🗃️ **Create database**:
   ```sql
   CREATE DATABASE jewellers;
   ```
4. 👤 **Create user** (if needed):
   ```sql
   CREATE USER postgres WITH PASSWORD 'admin';
   GRANT ALL PRIVILEGES ON DATABASE jewellers TO postgres;
   ```

### Docker PostgreSQL (Recommended)

The Docker setup automatically handles database creation.

## ⚙️ Installation

### 📥 Clone & Setup

```bash
# Clone repository (if applicable)
git clone <repository-url>
cd raj-backend-service

# Or ensure you have the project files in current directory
ls -la  # Should see pom.xml, src/, etc.
```

## 🔧 Configuration

### 📁 Application Profiles

The application supports multiple profiles for different environments:

| Profile | Description | Default |
|---------|-------------|---------|
| `dev` | Development configuration | ✅ |
| `prod` | Production configuration | ❌ |

### ⚙️ Key Configuration Properties

| Property | Value | Description |
|----------|-------|-------------|
| `server.port` | `8888` | Application port |
| `server.servlet.context-path` | `/raj-service` | API context path |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/jewellers` | Database URL |
| `spring.datasource.username` | `postgres` | Database username |
| `spring.datasource.password` | `admin` | Database password |
| `spring.profiles.active` | `dev` | Active profile |

### 🌍 Environment Variables

Override default configuration using environment variables:

```bash
# Database Configuration
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/jewellers
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=admin

# Application Configuration
export SERVER_PORT=8888
export SPRING_PROFILES_ACTIVE=dev
```

## Running the Application

### Using Docker Compose (Recommended)

```bash
# Build and run with Docker Compose
docker-compose up --build
```

This will start both the application and PostgreSQL database.

### Using Docker Only

```bash
# Build the image
docker build -t raj-jewellers .

# Run with external database
docker run -p 8888:8888 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/jewellers \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=admin \
  raj-jewellers
```

### Using Maven Wrapper (Local Development)

```bash
# Make the Maven wrapper executable (first time only)
chmod +x mvnw

# Run the application
./mvnw spring-boot:run
```

### Using Maven (if installed globally)

```bash
mvn spring-boot:run
```

### Using IDE

- Import the project as a Maven project
- Run the main class: `com.raj.jewellers.JewellersApplication`

## 📡 API Endpoints

**Base URL:** `http://localhost:8888/raj-service`

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| 🔐 **Authentication** | | | | |
| `POST` | `/api/login` | User authentication | `LoginRequestDto` | User details |
| 💍 **Jewellery Items** | | | | |
| `POST` | `/api/saveItems` | Create new item with images | `JewelleryItemRequestDTO` + Multipart files | Created item |
| `GET` | `/api/items` | Retrieve all items | - | Item list |
| `DELETE` | `/api/item?itemId={id}` | Delete item by ID | - | Success message |
| 📊 **Dashboard** | | | | |
| `GET` | `/api/dashboardCount` | Get statistics | - | Dashboard data |
| 📂 **Categories** | | | | |
| `GET` | `/api/categories` | Get all categories | - | Category list |
| `POST` | `/api/addcategory` | Create new category | `CategoryDTO` | Created category |
| 🔧 **Materials** | | | | |
| `GET` | `/api/materials` | Get all materials | - | Material list |
| `POST` | `/api/addMaterials` | Create new material | `MaterialDTO` | Created material |
| 🏠 **Root** | | | | |
| `GET` | `/` | Welcome message | - | Welcome response |

## 📋 API Response Format

All API responses follow a consistent JSON structure:

```json
{
  "message": "Operation completed successfully",
  "status": 200,
  "data": {
    // Response data object or array
  },
  "error": "FALSE"
}
```

### Response Fields

| Field | Type | Description |
|-------|------|-------------|
| `message` | String | Human-readable response message |
| `status` | Integer | HTTP status code |
| `data` | Object/Array | Response payload |
| `error` | String | Error flag ("TRUE" or "FALSE") |

## 🧪 Testing the API

### 🛠️ Recommended Tools

- 📮 **Postman** - GUI API testing
- 💻 **curl** - Command-line HTTP client
- 🌐 **Thunder Client** - VS Code extension
- 📚 **Swagger UI** - Interactive documentation (if configured)

### 📝 Example Requests

**Get Welcome Message:**
```bash
curl -X GET http://localhost:8888/raj-service/ \
  -H "Content-Type: application/json"
```

**Login Request:**
```bash
curl -X POST http://localhost:8888/raj-service/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "your-username",
    "password": "your-password"
  }'
```

**Get All Items:**
```bash
curl -X GET http://localhost:8888/raj-service/api/items \
  -H "Content-Type: application/json"
```

## 🏗️ Project Structure

```
raj-backend-service/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/raj/jewellers/
│   │   │   ├── JewellersApplication.java          # 🚀 Main application class
│   │   │   ├── 📁 config/                          # ⚙️ Configuration classes
│   │   │   ├── 📁 constants/                       # 🔧 Application constants
│   │   │   ├── 📁 controller/                      # 🎯 REST controllers
│   │   │   ├── 📁 dto/                            # 📦 Data transfer objects
│   │   │   ├── 📁 entity/                         # 🗃️ JPA entities
│   │   │   ├── 📁 enums/                          # 🏷️ Enumeration types
│   │   │   ├── 📁 exception/                      # ⚠️ Custom exceptions
│   │   │   ├── 📁 repository/                     # 💾 Data repositories
│   │   │   ├── 📁 response/                       # 📤 Response handlers
│   │   │   ├── 📁 service/                        # 🔄 Business logic
│   │   │   └── 📁 utility/                        # 🛠️ Utility classes
│   │   └── 📁 resources/
│   │       ├── application.properties            # ⚙️ Default properties
│   │       ├── application-dev.properties       # 🛠️ Development config
│   │       └── application-prod.properties      # 🚀 Production config
│   └── 📁 test/
│       └── 📁 java/com/raj/jewellers/            # 🧪 Test classes
├── 🐳 Dockerfile                                  # 🐳 Docker build file
├── 🐳 docker-compose.yml                         # 🐳 Docker orchestration
├── 📄 .dockerignore                              # 🚫 Docker ignore rules
├── 📄 README.md                                  # 📖 This file
├── 📦 pom.xml                                    # 📦 Maven configuration
├── 📦 mvnw & mvnw.cmd                           # 📦 Maven wrapper
└── 📄 .gitignore                                # 🚫 Git ignore rules
```

## 🐳 Docker Setup

### Prerequisites

- Docker
- Docker Compose

### Files

- `Dockerfile` - Multi-stage build for the Spring Boot application
- `docker-compose.yml` - Orchestrates the application and PostgreSQL database
- `.dockerignore` - Excludes unnecessary files from Docker build context

### Docker Compose Services

- **app**: Spring Boot application on port 8888
- **db**: PostgreSQL 15 database on port 5432

### Environment Variables

The Docker setup uses the following environment variables:

- `SPRING_PROFILES_ACTIVE=dev`
- `SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/jewellers`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=admin`

### Building and Running

```bash
# Build and start services
docker-compose up --build

# Run in background
docker-compose up -d --build

# Stop services
docker-compose down

# View logs
docker-compose logs -f app
```

## 🛠️ Technologies Used

| Technology | Version | Purpose | Category |
|------------|---------|---------|----------|
| ☕ **Java** | 17 | Programming language | Language |
| 🌱 **Spring Boot** | 3.5.5 | Application framework | Framework |
| 💾 **Spring Data JPA** | 3.2.10 | Data persistence | ORM |
| 🐘 **PostgreSQL** | 15 | Relational database | Database |
| ☁️ **Cloudinary** | 1.37.0 | Image storage service | Cloud Service |
| 🔧 **Lombok** | Latest | Code generation | Library |
| 📦 **Maven** | 3.9.4 | Build automation | Build Tool |
| 🐳 **Docker** | Latest | Containerization | DevOps |
| 📊 **Spring Boot Actuator** | 3.5.5 | Monitoring | Framework |

## 🔧 Troubleshooting

### 🚨 Common Issues & Solutions

| Issue | Symptoms | Solution |
|-------|----------|----------|
| **Database Connection Failed** | App starts but DB operations fail | ✅ Ensure PostgreSQL is running<br>✅ Verify credentials in `application-dev.properties`<br>✅ Check database `jewellers` exists |
| **Port Already in Use** | `Port 8888 already in use` error | 🔄 Change port in properties:<br>`server.port=8889` |
| **Build Failures** | Maven compilation errors | 🧹 Clean and rebuild:<br>`./mvnw clean install` |
| **Docker Build Issues** | Container build fails | 📦 Ensure Docker daemon is running<br>🔍 Check Dockerfile syntax |
| **Memory Issues** | App crashes with OOM | ⚙️ Increase JVM memory:<br>`-Xmx1024m -Xms512m` |

### 🐛 Getting Help

- 📋 **Check Logs**: `docker-compose logs -f app`
- 🔍 **Health Check**: Visit `http://localhost:8888/raj-service/actuator/health`
- 📊 **Application Status**: Check terminal output for startup messages

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### 📋 Contribution Process

1. 🍴 **Fork** the repository
2. 🌿 **Create** a feature branch: `git checkout -b feature/amazing-feature`
3. 💻 **Make** your changes
4. 🧪 **Run tests**: `./mvnw test`
5. ✅ **Commit** changes: `git commit -m 'Add amazing feature'`
6. 📤 **Push** to branch: `git push origin feature/amazing-feature`
7. 🔄 **Open** a Pull Request

### 📝 Guidelines

- 📖 Follow existing code style
- 🧪 Add tests for new features
- 📚 Update documentation as needed
- ✅ Ensure all tests pass
- 🎯 Keep PRs focused on single features

## 📄 License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

**Made with ❤️ for the jewellery industry**

⭐ Star this repo if you find it helpful!

[⬆️ Back to Top](#-raj-jewellers-backend-service)

</div>
