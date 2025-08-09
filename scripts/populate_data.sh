#!/bin/bash

# Task Manager - Sample Data Population Script
# This script provides multiple ways to populate sample data

echo "🚀 Task Manager - Sample Data Population Script"
echo "================================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
API_BASE_URL="http://localhost:8080/api/v1/tasks"
H2_CONSOLE_URL="http://localhost:8080/h2-console"

# Function to check if API is running
check_api() {
    echo -e "${BLUE}Checking if API is running...${NC}"
    if curl -s -o /dev/null -w "%{http_code}" "$API_BASE_URL" | grep -q "200\|400"; then
        echo -e "${GREEN}✅ API is running at $API_BASE_URL${NC}"
        return 0
    else
        echo -e "${RED}❌ API is not running. Please start the application first.${NC}"
        echo -e "${YELLOW}Run: mvn spring-boot:run${NC}"
        return 1
    fi
}

# Function to populate data via REST API
populate_via_api() {
    echo -e "\n${BLUE}Populating data via REST API...${NC}"
    
    # Array of sample tasks
    declare -a tasks=(
        '{"title":"Implement User Authentication","description":"Add JWT-based authentication system","dueDate":"2024-02-15","status":"IN_PROGRESS","priority":"HIGH","category":"Security","assignedTo":"john.doe@company.com","estimatedHours":20,"createdBy":"manager@company.com"}'
        '{"title":"Build REST API Endpoints","description":"Create comprehensive REST API for task management","dueDate":"2024-02-10","status":"TODO","priority":"HIGH","category":"Development","assignedTo":"jane.smith@company.com","estimatedHours":16,"createdBy":"lead@company.com"}'
        '{"title":"Frontend Integration","description":"Integrate React frontend with API endpoints","dueDate":"2024-02-20","status":"TODO","priority":"MEDIUM","category":"Frontend","assignedTo":"frontend.dev@company.com","estimatedHours":24,"createdBy":"manager@company.com"}'
        '{"title":"Database Optimization","description":"Optimize database queries and add indexing","dueDate":"2024-02-08","status":"IN_PROGRESS","priority":"MEDIUM","category":"Database","assignedTo":"db.admin@company.com","estimatedHours":12,"createdBy":"architect@company.com"}'
        '{"title":"Unit Test Coverage","description":"Increase unit test coverage to 95%","dueDate":"2024-02-12","status":"TODO","priority":"MEDIUM","category":"Testing","assignedTo":"qa.engineer@company.com","estimatedHours":14,"createdBy":"qa.lead@company.com"}'
        '{"title":"Fix Memory Leak Issue","description":"Resolve memory leak in background processor","dueDate":"2024-01-25","status":"TODO","priority":"URGENT","category":"Bug Fix","assignedTo":"senior.dev@company.com","estimatedHours":8,"createdBy":"support@company.com"}'
        '{"title":"API Documentation","description":"Write comprehensive API documentation","dueDate":"2024-02-08","status":"IN_PROGRESS","priority":"MEDIUM","category":"Documentation","assignedTo":"tech.writer@company.com","estimatedHours":12,"createdBy":"manager@company.com"}'
        '{"title":"CI/CD Pipeline Setup","description":"Configure automated testing and deployment","dueDate":"2024-02-10","status":"TODO","priority":"HIGH","category":"DevOps","assignedTo":"devops.engineer@company.com","estimatedHours":18,"createdBy":"cto@company.com"}'
        '{"title":"Security Audit","description":"Conduct comprehensive security audit","dueDate":"2024-02-28","status":"TODO","priority":"HIGH","category":"Security","assignedTo":"security@company.com","estimatedHours":24,"createdBy":"ciso@company.com"}'
        '{"title":"Performance Testing","description":"Conduct load and stress testing","dueDate":"2024-02-18","status":"ON_HOLD","priority":"LOW","category":"Testing","assignedTo":"perf.tester@company.com","estimatedHours":16,"createdBy":"qa.lead@company.com"}'
        '{"title":"Mobile App Development","description":"Develop mobile app for iOS and Android","dueDate":"2024-03-15","status":"TODO","priority":"LOW","category":"Mobile","assignedTo":"mobile.dev@company.com","estimatedHours":80,"createdBy":"product@company.com"}'
        '{"title":"Code Review Process","description":"Establish code review guidelines","dueDate":"2024-01-20","status":"TODO","priority":"MEDIUM","category":"Process","assignedTo":"senior.dev@company.com","estimatedHours":6,"createdBy":"lead@company.com"}'
        '{"title":"User Interface Redesign","description":"Redesign UI based on user feedback","dueDate":"2024-03-10","status":"TODO","priority":"MEDIUM","category":"Design","assignedTo":"ux.designer@company.com","estimatedHours":30,"createdBy":"design.lead@company.com"}'
        '{"title":"Dependency Updates","description":"Update all project dependencies","dueDate":"2024-02-05","status":"TODO","priority":"LOW","category":"Maintenance","assignedTo":"john.doe@company.com","estimatedHours":4,"createdBy":"lead@company.com"}'
        '{"title":"Database Schema Design","description":"Design initial database schema","dueDate":"2024-01-15","status":"COMPLETED","priority":"HIGH","category":"Database","assignedTo":"db.admin@company.com","estimatedHours":16,"createdBy":"architect@company.com"}'
    )
    
    success_count=0
    total_count=${#tasks[@]}
    
    for task in "${tasks[@]}"; do
        echo -n "Creating task... "
        response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$API_BASE_URL" \
            -H "Content-Type: application/json" \
            -d "$task")
        
        if [ "$response" == "201" ]; then
            echo -e "${GREEN}✅${NC}"
            ((success_count++))
        else
            echo -e "${RED}❌ (HTTP $response)${NC}"
        fi
    done
    
    echo -e "\n${GREEN}Successfully created $success_count out of $total_count tasks${NC}"
}

# Function to show statistics
show_statistics() {
    echo -e "\n${BLUE}Fetching task statistics...${NC}"
    
    response=$(curl -s "$API_BASE_URL/statistics")
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}📊 Task Statistics:${NC}"
        echo "$response" | python3 -m json.tool 2>/dev/null || echo "$response"
    else
        echo -e "${RED}❌ Failed to fetch statistics${NC}"
    fi
}

# Function to show sample API calls
show_sample_calls() {
    echo -e "\n${BLUE}📋 Sample API calls you can try:${NC}"
    echo -e "${YELLOW}# Get all tasks${NC}"
    echo "curl '$API_BASE_URL'"
    echo ""
    echo -e "${YELLOW}# Get tasks with pagination${NC}"
    echo "curl '$API_BASE_URL?page=0&size=5&sortBy=priority&sortDir=desc'"
    echo ""
    echo -e "${YELLOW}# Filter high priority tasks${NC}"
    echo "curl '$API_BASE_URL/filter?priority=HIGH'"
    echo ""
    echo -e "${YELLOW}# Get overdue tasks${NC}"
    echo "curl '$API_BASE_URL/overdue'"
    echo ""
    echo -e "${YELLOW}# Search tasks${NC}"
    echo "curl '$API_BASE_URL/search?query=authentication'"
    echo ""
    echo -e "${YELLOW}# Get task statistics${NC}"
    echo "curl '$API_BASE_URL/statistics'"
}

# Function to display help
show_help() {
    echo -e "\n${BLUE}Usage:${NC}"
    echo "  $0 [option]"
    echo ""
    echo -e "${BLUE}Options:${NC}"
    echo "  api       Populate data via REST API calls"
    echo "  stats     Show current task statistics"
    echo "  examples  Show example API calls"
    echo "  sql       Display SQL script location"
    echo "  help      Show this help message"
    echo ""
    echo -e "${BLUE}Note:${NC}"
    echo "- The application automatically loads sample data on startup"
    echo "- Make sure the application is running before using API options"
    echo "- H2 Console available at: $H2_CONSOLE_URL"
}

# Main script logic
case "${1:-api}" in
    "api")
        if check_api; then
            populate_via_api
            show_statistics
            show_sample_calls
        fi
        ;;
    "stats")
        if check_api; then
            show_statistics
        fi
        ;;
    "examples")
        show_sample_calls
        ;;
    "sql")
        echo -e "${BLUE}SQL script location:${NC}"
        echo "scripts/populate_sample_data.sql"
        echo ""
        echo -e "${BLUE}To use the SQL script:${NC}"
        echo "1. Access H2 Console: $H2_CONSOLE_URL"
        echo "2. Use connection: jdbc:h2:mem:taskdb"
        echo "3. Run the SQL script"
        ;;
    "help"|"-h"|"--help")
        show_help
        ;;
    *)
        echo -e "${RED}Unknown option: $1${NC}"
        show_help
        exit 1
        ;;
esac

echo -e "\n${GREEN}🎉 Done!${NC}"
