# Task Manager API Documentation

## Overview
The Task Manager API provides comprehensive functionality for managing tasks with features like prioritization, categorization, assignment, filtering, and statistics.

## Base URL
```
http://localhost:8080/api/v1/tasks
```

## Features
- ✅ Full CRUD operations for tasks
- ✅ Advanced filtering and search
- ✅ Pagination and sorting
- ✅ Task status management (TODO, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED)
- ✅ Priority levels (LOW, MEDIUM, HIGH, URGENT)
- ✅ Categories and assignments
- ✅ Due date tracking and overdue detection
- ✅ Bulk operations
- ✅ Statistics and analytics
- ✅ Input validation and error handling

## Task Model
```json
{
  "id": 1,
  "title": "Complete API documentation",
  "description": "Write comprehensive API documentation with examples",
  "dueDate": "2024-01-15",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "category": "Documentation",
  "assignedTo": "john.doe@example.com",
  "estimatedHours": 8,
  "completionDate": null,
  "createdAt": "2024-01-10T09:00:00",
  "updatedAt": "2024-01-10T09:00:00",
  "createdBy": "admin@example.com"
}
```

## API Endpoints

### 1. Get All Tasks (with pagination)
```http
GET /api/v1/tasks?page=0&size=10&sortBy=createdAt&sortDir=desc
```

**Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 10)
- `sortBy` (optional): Sort field (default: createdAt)
- `sortDir` (optional): Sort direction (default: desc)

### 2. Get Task by ID
```http
GET /api/v1/tasks/{id}
```

### 3. Create Task
```http
POST /api/v1/tasks
Content-Type: application/json

{
  "title": "New Task",
  "description": "Task description",
  "dueDate": "2024-01-20",
  "priority": "MEDIUM",
  "category": "Development",
  "assignedTo": "developer@example.com",
  "estimatedHours": 5,
  "createdBy": "manager@example.com"
}
```

### 4. Update Task
```http
PUT /api/v1/tasks/{id}
Content-Type: application/json

{
  "title": "Updated Task",
  "description": "Updated description",
  "status": "COMPLETED",
  "priority": "HIGH"
}
```

### 5. Partial Update (Patch)
```http
PATCH /api/v1/tasks/{id}
Content-Type: application/json

{
  "status": "IN_PROGRESS",
  "assignedTo": "new.assignee@example.com"
}
```

### 6. Delete Task
```http
DELETE /api/v1/tasks/{id}
```

### 7. Mark Task as Completed
```http
PATCH /api/v1/tasks/{id}/complete
```

### 8. Start Task (Mark as In Progress)
```http
PATCH /api/v1/tasks/{id}/start
```

### 9. Advanced Filtering
```http
GET /api/v1/tasks/filter?status=TODO&priority=HIGH&category=Development&assignedTo=john.doe@example.com&searchTerm=API&page=0&size=10
```

**Filter Parameters:**
- `title`: Filter by title (partial match)
- `description`: Filter by description (partial match)
- `status`: Filter by status (TODO, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED)
- `priority`: Filter by priority (LOW, MEDIUM, HIGH, URGENT)
- `category`: Filter by category
- `assignedTo`: Filter by assignee
- `createdBy`: Filter by creator
- `createdAfter`: Filter by creation date (after)
- `createdBefore`: Filter by creation date (before)
- `dueAfter`: Filter by due date (after)
- `dueBefore`: Filter by due date (before)
- `searchTerm`: Search in title and description

### 10. Get Overdue Tasks
```http
GET /api/v1/tasks/overdue
```

### 11. Get Tasks Due Today
```http
GET /api/v1/tasks/due-today
```

### 12. Get Tasks Due Within N Days
```http
GET /api/v1/tasks/due-within?days=7
```

### 13. Get High Priority Pending Tasks
```http
GET /api/v1/tasks/high-priority
```

### 14. Search Tasks
```http
GET /api/v1/tasks/search?query=API documentation
```

### 15. Get Recently Updated Tasks
```http
GET /api/v1/tasks/recent?page=0&size=5
```

### 16. Get Tasks by Status
```http
GET /api/v1/tasks/status/TODO
GET /api/v1/tasks/status/IN_PROGRESS
GET /api/v1/tasks/status/COMPLETED
```

### 17. Get Tasks by Priority
```http
GET /api/v1/tasks/priority/HIGH
GET /api/v1/tasks/priority/URGENT
```

### 18. Get Tasks by Category
```http
GET /api/v1/tasks/category/Development
```

### 19. Get Tasks Assigned to User
```http
GET /api/v1/tasks/assigned/john.doe@example.com
```

### 20. Get Tasks Created by User
```http
GET /api/v1/tasks/created-by/manager@example.com
```

### 21. Get Task Statistics
```http
GET /api/v1/tasks/statistics
```

**Response:**
```json
{
  "statusCounts": {
    "TODO": 15,
    "IN_PROGRESS": 8,
    "COMPLETED": 25,
    "ON_HOLD": 2,
    "CANCELLED": 1
  },
  "priorityCounts": {
    "LOW": 10,
    "MEDIUM": 20,
    "HIGH": 15,
    "URGENT": 6
  },
  "categoryCounts": {
    "Development": 30,
    "Testing": 10,
    "Documentation": 8,
    "Bug Fix": 3
  },
  "assigneeCounts": {
    "john.doe@example.com": 15,
    "jane.smith@example.com": 12,
    "bob.johnson@example.com": 8
  },
  "overall": {
    "total": 51,
    "overdue": 3,
    "dueToday": 5
  }
}
```

### 22. Bulk Update Status
```http
POST /api/v1/tasks/bulk-update-status
Content-Type: application/json

{
  "taskIds": [1, 2, 3, 4],
  "status": "COMPLETED"
}
```

### 23. Bulk Delete Tasks
```http
DELETE /api/v1/tasks/bulk-delete
Content-Type: application/json

[1, 2, 3, 4]
```

## Error Handling

### Validation Errors (400)
```json
{
  "timestamp": "2024-01-10T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "path": "/api/v1/tasks",
  "validationErrors": {
    "title": "Title is required",
    "dueDate": "Due date must be in the future"
  }
}
```

### Not Found (404)
```json
{
  "timestamp": "2024-01-10T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: 999",
  "path": "/api/v1/tasks/999"
}
```

### Server Error (500)
```json
{
  "timestamp": "2024-01-10T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/v1/tasks"
}
```

## Status Codes
- `200 OK`: Success
- `201 Created`: Resource created
- `204 No Content`: Success with no response body
- `400 Bad Request`: Invalid input
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## Development Setup

### Prerequisites
- Java 21+
- Maven 3.6+

### Running the Application
```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

### H2 Console (Development)
Access the H2 database console at `http://localhost:8080/h2-console`
- URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: `password`

### Health Check
```http
GET /actuator/health
```

## Example Usage Scenarios

### 1. Create a New Project Task
```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Implement user authentication",
    "description": "Add JWT-based authentication to the API",
    "dueDate": "2024-01-25",
    "priority": "HIGH",
    "category": "Development",
    "assignedTo": "developer@example.com",
    "estimatedHours": 16,
    "createdBy": "manager@example.com"
  }'
```

### 2. Get All High Priority Tasks
```bash
curl "http://localhost:8080/api/v1/tasks/filter?priority=HIGH&status=TODO"
```

### 3. Complete a Task
```bash
curl -X PATCH http://localhost:8080/api/v1/tasks/1/complete
```

### 4. Get Team Statistics
```bash
curl http://localhost:8080/api/v1/tasks/statistics
```

## Future Enhancements
- User authentication and authorization
- File attachments for tasks
- Task comments and activity log
- Email notifications
- Task dependencies
- Recurring tasks
- Time tracking
- Kanban board view
- REST API versioning
- Caching for performance
