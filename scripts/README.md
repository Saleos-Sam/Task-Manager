# Task Manager - Sample Data Population Scripts

This directory contains various scripts to populate your Task Manager database with comprehensive sample data for testing and demonstration purposes.

## 📁 Available Scripts

### 1. **Automatic Data Loading** (Recommended)
The application automatically loads sample data when started in `local` profile (default).

```bash
mvn spring-boot:run
```

**Benefits:**
- ✅ No additional setup required
- ✅ Runs automatically on application startup
- ✅ Only loads data if database is empty
- ✅ Comprehensive sample data with various scenarios

### 2. **SQL Script** (`populate_sample_data.sql`)
Direct SQL script for manual database population.

**Usage:**
1. Start the application: `mvn spring-boot:run`
2. Access H2 Console: http://localhost:8080/h2-console
3. Connect with:
   - **JDBC URL**: `jdbc:h2:mem:taskdb`
   - **Username**: `sa`
   - **Password**: `password`
4. Copy and paste the SQL script content
5. Execute the script

**Features:**
- 30+ comprehensive sample tasks
- Various statuses, priorities, and categories
- Realistic due dates (including overdue tasks)
- Multiple team members and assignments
- Covers all application features

### 3. **Shell Script** (`populate_data.sh`)
Interactive bash script for Linux/macOS/WSL users.

**Usage:**
```bash
# Make executable (first time only)
chmod +x scripts/populate_data.sh

# Populate data via API
./scripts/populate_data.sh api

# Show current statistics
./scripts/populate_data.sh stats

# Show example API calls
./scripts/populate_data.sh examples

# Show help
./scripts/populate_data.sh help
```

**Features:**
- ✅ API health checking
- ✅ Progress indication with colors
- ✅ Error handling and reporting
- ✅ Automatic statistics display
- ✅ Sample API call examples

### 4. **Python Script** (`populate_data.py`)
Cross-platform Python script with advanced features.

**Prerequisites:**
```bash
pip install requests
```

**Usage:**
```bash
# Populate data (default action)
python scripts/populate_data.py

# Show statistics only
python scripts/populate_data.py stats

# Show example API calls
python scripts/populate_data.py examples

# Show help
python scripts/populate_data.py help
```

**Features:**
- ✅ Cross-platform compatibility
- ✅ Comprehensive error handling
- ✅ Colored console output
- ✅ Detailed progress reporting
- ✅ Automatic API health checking
- ✅ Rich sample data generation

## 📊 Sample Data Overview

The scripts create a comprehensive dataset including:

### **Task Categories**
- **Development**: 5 tasks (API, Frontend, Authentication, etc.)
- **Testing**: 3 tasks (Unit tests, Performance, Integration)
- **Bug Fix**: 3 tasks (Memory leak, Database timeout, UI issues)
- **Documentation**: 2 tasks (API docs, User manual)
- **DevOps**: 2 tasks (CI/CD, Monitoring)
- **Security**: 2 tasks (Audit, Encryption)
- **Database**: 2 tasks (Schema, Optimization)
- **Design**: 1 task (UI Redesign)
- **Maintenance**: 1 task (Dependencies)
- **Mobile**: 1 task (App Development)

### **Task Statuses**
- **TODO**: 15 tasks
- **IN_PROGRESS**: 5 tasks
- **COMPLETED**: 3 tasks
- **ON_HOLD**: 2 tasks
- **CANCELLED**: 0 tasks

### **Priority Levels**
- **URGENT**: 1 task (Critical bug)
- **HIGH**: 7 tasks (Important features/fixes)
- **MEDIUM**: 12 tasks (Regular development)
- **LOW**: 5 tasks (Nice-to-have features)

### **Team Members**
- john.doe@company.com
- jane.smith@company.com
- frontend.dev@company.com
- db.admin@company.com
- qa.engineer@company.com
- senior.dev@company.com
- tech.writer@company.com
- devops.engineer@company.com
- security@company.com
- ux.designer@company.com
- mobile.dev@company.com

### **Realistic Scenarios**
- ✅ **Overdue tasks** (2 tasks past due date)
- ✅ **Today's tasks** (Tasks due today)
- ✅ **Upcoming deadlines** (Tasks due within a week)
- ✅ **Completed tasks** (With completion dates)
- ✅ **Various time estimates** (2-80 hours)
- ✅ **Cross-team assignments**

## 🧪 Testing the Data

After populating data, you can test various API endpoints:

### **Basic Operations**
```bash
# Get all tasks
curl "http://localhost:8080/api/v1/tasks"

# Get task by ID
curl "http://localhost:8080/api/v1/tasks/1"

# Get task statistics
curl "http://localhost:8080/api/v1/tasks/statistics"
```

### **Filtering & Search**
```bash
# High priority tasks
curl "http://localhost:8080/api/v1/tasks/filter?priority=HIGH"

# Development tasks
curl "http://localhost:8080/api/v1/tasks/filter?category=Development"

# Tasks assigned to John
curl "http://localhost:8080/api/v1/tasks/filter?assignedTo=john.doe@company.com"

# Search for authentication
curl "http://localhost:8080/api/v1/tasks/search?query=authentication"
```

### **Special Views**
```bash
# Overdue tasks
curl "http://localhost:8080/api/v1/tasks/overdue"

# Tasks due today
curl "http://localhost:8080/api/v1/tasks/due-today"

# High priority pending tasks
curl "http://localhost:8080/api/v1/tasks/high-priority"

# Recently updated tasks
curl "http://localhost:8080/api/v1/tasks/recent"
```

## 🔧 Customization

### **Modifying Sample Data**

1. **SQL Script**: Edit `populate_sample_data.sql` directly
2. **Python Script**: Modify the `get_sample_tasks()` function
3. **Shell Script**: Update the `tasks` array
4. **Auto-loader**: Edit `DataLoader.java` in the main application
