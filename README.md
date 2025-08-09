# Task Manager API

A comprehensive REST API for task management with features like prioritization, categorization, assignment tracking, filtering, and analytics.

## 🚀 Features

- **Complete CRUD Operations**: Create, read, update, and delete tasks
- **Advanced Filtering**: Filter by status, priority, category, assignee, due dates, and more
- **Search Functionality**: Full-text search across task titles and descriptions
- **Pagination & Sorting**: Efficient data retrieval with customizable pagination
- **Task Status Management**: TODO, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED
- **Priority Levels**: LOW, MEDIUM, HIGH, URGENT
- **Due Date Tracking**: Overdue detection and upcoming task notifications
- **Assignment Management**: Assign tasks to team members
- **Bulk Operations**: Update or delete multiple tasks at once
- **Statistics & Analytics**: Comprehensive task statistics and reports
- **Input Validation**: Robust validation with detailed error messages
- **Audit Trail**: Track creation and modification timestamps

## 🛠️ Technologies Used

- **Java 21**: Latest LTS version of Java
- **Spring Boot 3.5.4**: Application framework
- **Spring Data JPA**: Data persistence layer
- **Hibernate**: ORM framework
- **H2 Database**: In-memory database for development
- **PostgreSQL**: Production database support
- **Maven**: Build and dependency management
- **Lombok**: Reduce boilerplate code
- **Bean Validation**: Input validation
- **Spring Boot Actuator**: Application monitoring

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## 🏃‍♂️ Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd TaskManager
```

### 2. Run the Application
```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

### 3. Access H2 Database Console (Development)
Visit `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:taskdb`
- **Username**: `sa`
- **Password**: `password`

### 4. Test the API
The application loads sample data automatically. You can test the endpoints:

```bash
# Get all tasks
curl http://localhost:8080/api/v1/tasks

# Get task statistics
curl http://localhost:8080/api/v1/tasks/statistics

# Create a new task
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Task",
    "description": "Task description",
    "dueDate": "2024-02-01",
    "priority": "HIGH",
    "category": "Development"
  }'
```

## 📚 API Documentation

For complete API documentation with all endpoints, parameters, and examples, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md).

### Key Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/tasks` | Get all tasks (paginated) |
| POST | `/api/v1/tasks` | Create a new task |
| GET | `/api/v1/tasks/{id}` | Get task by ID |
| PUT | `/api/v1/tasks/{id}` | Update task |
| DELETE | `/api/v1/tasks/{id}` | Delete task |
| GET | `/api/v1/tasks/filter` | Advanced filtering |
| GET | `/api/v1/tasks/overdue` | Get overdue tasks |
| GET | `/api/v1/tasks/statistics` | Get task statistics |

## 🔧 Configuration

### Development Profile (default)
- Uses H2 in-memory database
- Loads sample data automatically
- Detailed logging enabled
- H2 console accessible

### Production Profile
- Uses PostgreSQL database
- Optimized logging
- Security configurations

### Environment Variables
```bash
# Database configuration (for production)
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/taskdb
SPRING_DATASOURCE_USERNAME=taskuser
SPRING_DATASOURCE_PASSWORD=taskpass

# Application port
SERVER_PORT=8080

# Active profile
SPRING_PROFILES_ACTIVE=local
```

## 🐳 Docker Support

### Using Docker Compose
```bash
docker-compose up -d
```

### Manual Docker Build
```bash
docker build -t task-manager .
docker run -p 8080:8080 task-manager
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Health Check
```bash
curl http://localhost:8080/actuator/health
```

## 📊 Monitoring

The application includes Spring Boot Actuator for monitoring:

- **Health**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`

## 🎯 Sample Data

The application automatically loads sample tasks including:
- Various task statuses and priorities
- Different categories (Security, Documentation, Bug Fix, etc.)
- Multiple assignees
- Tasks with different due dates (including overdue tasks)

## 🔍 Example Usage

### Create a Task
```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Implement user authentication",
    "description": "Add JWT-based authentication to the API",
    "dueDate": "2024-01-25",
    "priority": "HIGH",
    "category": "Security",
    "assignedTo": "developer@example.com",
    "estimatedHours": 16
  }'
```

### Filter Tasks
```bash
# Get high priority pending tasks
curl "http://localhost:8080/api/v1/tasks/filter?priority=HIGH&status=TODO"

# Search tasks
curl "http://localhost:8080/api/v1/tasks/search?query=authentication"

# Get overdue tasks
curl http://localhost:8080/api/v1/tasks/overdue
```

### Get Statistics
```bash
curl http://localhost:8080/api/v1/tasks/statistics
```

## 🔮 Future Enhancements

- User authentication and authorization
- File attachments for tasks
- Task comments and activity log
- Email notifications
- Task dependencies
- Recurring tasks
- Time tracking
- Real-time updates with WebSocket
- API rate limiting
- Caching for performance