# Support Ticket Management System

A RESTful API for managing support tickets built with **Spring Boot 4.0.2**.

## 🚀 Features

- **CRUD Operations** - Create, Read, Update, and Delete support tickets
- **Swagger UI** - Interactive API documentation
- **H2 Database** - In-memory database for development
- **Clean Architecture** - Controller → Service → Repository layers

## 📋 Tech Stack

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **H2 Database**
- **SpringDoc OpenAPI (Swagger)**
- **Lombok**
- **JUnit 5 & MockMvc** for testing


## 🛠️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

```bash
# Clone the repository
git clone <repository-url>
cd supportticketmanagement

# Run the application
./mvnw spring-boot:run
```

The application will start on **http://localhost:8081**

### Running Tests

```bash
./mvnw test
```

## 📚 API Documentation

### Swagger UI

Access the interactive API documentation at:
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/v3/api-docs
- **OpenAPI YAML**: http://localhost:8081/v3/api-docs.yaml

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/supporttickets` | Get all support tickets |
| `GET` | `/supporttickets/{id}` | Get a specific ticket by ID |
| `POST` | `/supporttickets` | Create a new ticket |
| `PUT` | `/supporttickets/{id}` | Update an existing ticket |
| `DELETE` | `/supporttickets/{id}` | Delete a ticket |

### Sample Request Body

```json
{
  "customerId": 123,
  "catagory": "Technical Support",
  "assignedTo": "John Doe",
  "status": "OPEN",
  "priority": "HIGH"
}
```

### Status Values

| Status | Description |
|--------|-------------|
| `OPEN` | Ticket is newly created |
| `IN_PROGRESS` | Ticket is being worked on |
| `RESOLVED` | Issue has been resolved |
| `CLOSED` | Ticket is closed |

### Priority Values

| Priority | Description |
|----------|-------------|
| `HIGH` | Urgent issue |
| `MEDIUM` | Normal priority |
| `LOW` | Low priority |

## 🧪 Testing

The project includes comprehensive integration tests using MockMvc:

- **POST Tests** - Creating tickets with various statuses/priorities
- **GET Tests** - Retrieving single and all tickets, handling 404s
- **PUT Tests** - Updating existing tickets, creating if not found
- **DELETE Tests** - Removing tickets

## 📁 H2 Console

Access the H2 database console at: http://localhost:8081/h2-console

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave empty)

## 📝 License

This project is licensed under the Apache 2.0 License.
