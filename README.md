# Raj Jewellers Backend Service

A Spring Boot-based REST API service for managing a jewellers inventory system, including jewellery items, categories, materials, and user authentication.

## Features

- User authentication and login
- Jewellery item management (CRUD operations)
- Category and material management
- Image upload via Cloudinary integration
- Dashboard statistics
- RESTful API endpoints

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git (optional, for cloning)

## Database Setup

1. Install and start PostgreSQL
2. Create a database named `jewellers`
3. Ensure the database user `postgres` with password `admin` has access to the database
4. The application uses schema `public` by default

## Installation

1. Clone the repository (if applicable) or ensure you have the project files
2. Navigate to the project root directory

## Configuration

The application uses different profiles for different environments:

- `dev` (default): Development configuration
- `prod`: Production configuration

### Application Properties

Key configuration in `application-dev.properties`:

- **Server**: Port 8888, Context path `/raj-service`
- **Database**: PostgreSQL on localhost:5432
- **Cloudinary**: Configured for image uploads

### Environment Variables (Optional)

You can override database credentials using environment variables:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/jewellers
export DB_USERNAME=postgres
export DB_PASSWORD=admin
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

## API Endpoints

The API is available at `http://localhost:8888/raj-service`

### Authentication

- `POST /api/login` - User login

### Jewellery Items

- `POST /api/saveItems` - Save new jewellery item (with images)
- `GET /api/items` - Get all jewellery items
- `DELETE /api/item?itemId={id}` - Delete item by ID

### Dashboard

- `GET /api/dashboardCount` - Get dashboard statistics

### Categories

- `GET /api/categories` - Get all categories
- `POST /api/addcategory` - Add new category

### Materials

- `GET /api/materials` - Get all materials
- `POST /api/addMaterials` - Add new material

### Root

- `GET /` - Welcome message

## API Response Format

All API responses follow a consistent format:

```json
{
  "message": "Success message",
  "status": 200,
  "data": { ... },
  "error": "FALSE"
}
```

## Testing the API

You can test the endpoints using tools like:

- Postman
- curl
- Swagger UI (if configured)

Example curl command:

```bash
curl -X GET http://localhost:8888/raj-service/
```

## Project Structure

```
src/
├── main/
│   ├── java/com/raj/jewellers/
│   │   ├── JewellersApplication.java
│   │   ├── config/
│   │   ├── constants/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── enums/
│   │   ├── exception/
│   │   ├── repository/
│   │   ├── response/
│   │   ├── service/
│   │   └── utility/
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/
    └── java/com/raj/jewellers/
```

## Docker Setup

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

## Technologies Used

- **Spring Boot 3.5.5** - Framework
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Database
- **Cloudinary** - Image storage
- **Lombok** - Code generation
- **Maven** - Build tool
- **Docker** - Containerization

## Troubleshooting

### Database Connection Issues

- Ensure PostgreSQL is running on port 5432
- Verify database credentials in `application-dev.properties`
- Check that the `jewellers` database exists

### Port Already in Use

- Change the port in `application-dev.properties`:
  ```properties
  server.port=8889
  ```

### Build Issues

- Clean and rebuild:
  ```bash
  ./mvnw clean install
  ```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.