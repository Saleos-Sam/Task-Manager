#!/usr/bin/env python3
"""
Task Manager - Sample Data Population Script (Python)
This script populates the Task Manager API with comprehensive sample data
"""

import requests
import json
import sys
from datetime import datetime, timedelta
from typing import List, Dict, Any

# Configuration
API_BASE_URL = "http://localhost:8080/api/v1/tasks"
TIMEOUT = 10

# Color codes for console output
class Colors:
    RED = '\033[0;31m'
    GREEN = '\033[0;32m'
    BLUE = '\033[0;34m'
    YELLOW = '\033[1;33m'
    BOLD = '\033[1m'
    NC = '\033[0m'  # No Color

def print_colored(message: str, color: str) -> None:
    """Print colored message to console"""
    print(f"{color}{message}{Colors.NC}")

def check_api_health() -> bool:
    """Check if the API is running and accessible"""
    try:
        print_colored("🔍 Checking API health...", Colors.BLUE)
        response = requests.get(API_BASE_URL, timeout=TIMEOUT)
        if response.status_code in [200, 400]:  # 400 is expected for GET with pagination
            print_colored("✅ API is running and accessible", Colors.GREEN)
            return True
    except requests.exceptions.RequestException as e:
        print_colored(f"❌ API is not accessible: {e}", Colors.RED)
        print_colored("Please ensure the application is running: mvn spring-boot:run", Colors.YELLOW)
    return False

def get_sample_tasks() -> List[Dict[str, Any]]:
    """Generate comprehensive sample task data"""
    base_date = datetime.now()
    
    return [
        # Development Tasks
        {
            "title": "Implement User Authentication",
            "description": "Add JWT-based authentication system with role-based access control and session management",
            "dueDate": (base_date + timedelta(days=15)).strftime("%Y-%m-%d"),
            "status": "IN_PROGRESS",
            "priority": "HIGH",
            "category": "Security",
            "assignedTo": "john.doe@company.com",
            "estimatedHours": 20,
            "createdBy": "manager@company.com"
        },
        {
            "title": "Build REST API Endpoints",
            "description": "Create comprehensive REST API with full CRUD operations, filtering, and pagination",
            "dueDate": (base_date + timedelta(days=10)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "HIGH",
            "category": "Development",
            "assignedTo": "jane.smith@company.com",
            "estimatedHours": 16,
            "createdBy": "lead@company.com"
        },
        {
            "title": "Frontend Integration",
            "description": "Integrate React frontend with the new API endpoints and implement responsive design",
            "dueDate": (base_date + timedelta(days=20)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "MEDIUM",
            "category": "Frontend",
            "assignedTo": "frontend.dev@company.com",
            "estimatedHours": 24,
            "createdBy": "manager@company.com"
        },
        {
            "title": "Database Optimization",
            "description": "Optimize database queries, add proper indexing, and implement connection pooling",
            "dueDate": (base_date + timedelta(days=8)).strftime("%Y-%m-%d"),
            "status": "IN_PROGRESS",
            "priority": "MEDIUM",
            "category": "Database",
            "assignedTo": "db.admin@company.com",
            "estimatedHours": 12,
            "createdBy": "architect@company.com"
        },
        {
            "title": "Mobile App Development",
            "description": "Develop native mobile applications for iOS and Android platforms",
            "dueDate": (base_date + timedelta(days=45)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "LOW",
            "category": "Mobile",
            "assignedTo": "mobile.dev@company.com",
            "estimatedHours": 80,
            "createdBy": "product@company.com"
        },
        
        # Testing Tasks
        {
            "title": "Unit Test Coverage",
            "description": "Increase unit test coverage to 95% for all critical components and services",
            "dueDate": (base_date + timedelta(days=12)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "MEDIUM",
            "category": "Testing",
            "assignedTo": "qa.engineer@company.com",
            "estimatedHours": 14,
            "createdBy": "qa.lead@company.com"
        },
        {
            "title": "Performance Testing",
            "description": "Conduct comprehensive load testing and stress testing for the application",
            "dueDate": (base_date + timedelta(days=18)).strftime("%Y-%m-%d"),
            "status": "ON_HOLD",
            "priority": "LOW",
            "category": "Testing",
            "assignedTo": "perf.tester@company.com",
            "estimatedHours": 16,
            "createdBy": "qa.lead@company.com"
        },
        {
            "title": "Integration Testing",
            "description": "Set up automated integration tests for all API endpoints and workflows",
            "dueDate": (base_date + timedelta(days=14)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "MEDIUM",
            "category": "Testing",
            "assignedTo": "qa.engineer@company.com",
            "estimatedHours": 10,
            "createdBy": "qa.lead@company.com"
        },
        
        # Bug Fixes
        {
            "title": "Fix Memory Leak Issue",
            "description": "Resolve critical memory leak in the background task processor affecting performance",
            "dueDate": (base_date - timedelta(days=5)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "URGENT",
            "category": "Bug Fix",
            "assignedTo": "senior.dev@company.com",
            "estimatedHours": 8,
            "createdBy": "support@company.com"
        },
        {
            "title": "Resolve Database Timeout",
            "description": "Fix intermittent database connection timeout errors in production environment",
            "dueDate": (base_date - timedelta(days=2)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "HIGH",
            "category": "Bug Fix",
            "assignedTo": "db.admin@company.com",
            "estimatedHours": 6,
            "createdBy": "support@company.com"
        },
        {
            "title": "UI Responsiveness Bug",
            "description": "Fix responsive design issues affecting mobile and tablet users",
            "dueDate": (base_date + timedelta(days=5)).strftime("%Y-%m-%d"),
            "status": "IN_PROGRESS",
            "priority": "MEDIUM",
            "category": "Bug Fix",
            "assignedTo": "frontend.dev@company.com",
            "estimatedHours": 4,
            "createdBy": "ux.designer@company.com"
        },
        
        # Documentation Tasks
        {
            "title": "API Documentation",
            "description": "Write comprehensive API documentation with examples, use cases, and best practices",
            "dueDate": (base_date + timedelta(days=8)).strftime("%Y-%m-%d"),
            "status": "IN_PROGRESS",
            "priority": "MEDIUM",
            "category": "Documentation",
            "assignedTo": "tech.writer@company.com",
            "estimatedHours": 12,
            "createdBy": "manager@company.com"
        },
        {
            "title": "User Manual",
            "description": "Create comprehensive user manual and help documentation for end users",
            "dueDate": (base_date + timedelta(days=22)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "LOW",
            "category": "Documentation",
            "assignedTo": "tech.writer@company.com",
            "estimatedHours": 20,
            "createdBy": "product@company.com"
        },
        
        # DevOps Tasks
        {
            "title": "CI/CD Pipeline Setup",
            "description": "Configure automated testing and deployment pipeline with Jenkins and Docker",
            "dueDate": (base_date + timedelta(days=10)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "HIGH",
            "category": "DevOps",
            "assignedTo": "devops.engineer@company.com",
            "estimatedHours": 18,
            "createdBy": "cto@company.com"
        },
        {
            "title": "Monitoring Setup",
            "description": "Implement application monitoring with Prometheus, Grafana, and alerting system",
            "dueDate": (base_date + timedelta(days=25)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "MEDIUM",
            "category": "DevOps",
            "assignedTo": "sre.engineer@company.com",
            "estimatedHours": 14,
            "createdBy": "devops.lead@company.com"
        },
        
        # Security Tasks
        {
            "title": "Security Audit",
            "description": "Conduct comprehensive security audit and penetration testing of the application",
            "dueDate": (base_date + timedelta(days=28)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "HIGH",
            "category": "Security",
            "assignedTo": "security@company.com",
            "estimatedHours": 24,
            "createdBy": "ciso@company.com"
        },
        
        # Completed Tasks
        {
            "title": "Database Schema Design",
            "description": "Design and implement the initial database schema with proper relationships",
            "dueDate": (base_date - timedelta(days=15)).strftime("%Y-%m-%d"),
            "status": "COMPLETED",
            "priority": "HIGH",
            "category": "Database",
            "assignedTo": "db.admin@company.com",
            "estimatedHours": 16,
            "createdBy": "architect@company.com"
        },
        {
            "title": "Project Setup",
            "description": "Initialize project structure, configure build tools, and set up development environment",
            "dueDate": (base_date - timedelta(days=20)).strftime("%Y-%m-%d"),
            "status": "COMPLETED",
            "priority": "MEDIUM",
            "category": "Setup",
            "assignedTo": "lead@company.com",
            "estimatedHours": 8,
            "createdBy": "manager@company.com"
        },
        
        # Maintenance Tasks
        {
            "title": "Dependency Updates",
            "description": "Update all project dependencies to latest stable versions and resolve conflicts",
            "dueDate": (base_date + timedelta(days=5)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "LOW",
            "category": "Maintenance",
            "assignedTo": "john.doe@company.com",
            "estimatedHours": 4,
            "createdBy": "lead@company.com"
        },
        
        # Design Tasks
        {
            "title": "User Interface Redesign",
            "description": "Redesign user interface based on user feedback and modern design principles",
            "dueDate": (base_date + timedelta(days=35)).strftime("%Y-%m-%d"),
            "status": "TODO",
            "priority": "MEDIUM",
            "category": "Design",
            "assignedTo": "ux.designer@company.com",
            "estimatedHours": 30,
            "createdBy": "design.lead@company.com"
        }
    ]

def create_task(task_data: Dict[str, Any]) -> bool:
    """Create a single task via API"""
    try:
        response = requests.post(
            API_BASE_URL,
            json=task_data,
            headers={"Content-Type": "application/json"},
            timeout=TIMEOUT
        )
        return response.status_code == 201
    except requests.exceptions.RequestException:
        return False

def populate_tasks() -> None:
    """Populate database with sample tasks"""
    print_colored("🚀 Starting data population...", Colors.BLUE)
    
    sample_tasks = get_sample_tasks()
    success_count = 0
    total_count = len(sample_tasks)
    
    for i, task in enumerate(sample_tasks, 1):
        print(f"Creating task {i}/{total_count}: {task['title'][:50]}...", end=" ")
        
        if create_task(task):
            print_colored("✅", Colors.GREEN)
            success_count += 1
        else:
            print_colored("❌", Colors.RED)
    
    print_colored(f"\n📊 Results: {success_count}/{total_count} tasks created successfully", Colors.BLUE)

def get_statistics() -> None:
    """Fetch and display task statistics"""
    try:
        print_colored("📈 Fetching task statistics...", Colors.BLUE)
        response = requests.get(f"{API_BASE_URL}/statistics", timeout=TIMEOUT)
        
        if response.status_code == 200:
            stats = response.json()
            print_colored("📊 Task Statistics:", Colors.GREEN)
            print(json.dumps(stats, indent=2))
        else:
            print_colored(f"❌ Failed to fetch statistics (HTTP {response.status_code})", Colors.RED)
    except requests.exceptions.RequestException as e:
        print_colored(f"❌ Error fetching statistics: {e}", Colors.RED)

def show_sample_api_calls() -> None:
    """Display sample API calls for testing"""
    print_colored("\n🔧 Sample API calls you can try:", Colors.BLUE)
    
    examples = [
        ("Get all tasks", f"curl '{API_BASE_URL}'"),
        ("Get paginated tasks", f"curl '{API_BASE_URL}?page=0&size=5&sortBy=priority&sortDir=desc'"),
        ("Filter high priority tasks", f"curl '{API_BASE_URL}/filter?priority=HIGH'"),
        ("Get overdue tasks", f"curl '{API_BASE_URL}/overdue'"),
        ("Search tasks", f"curl '{API_BASE_URL}/search?query=authentication'"),
        ("Get task statistics", f"curl '{API_BASE_URL}/statistics'"),
        ("Get tasks due today", f"curl '{API_BASE_URL}/due-today'"),
        ("Get high priority pending tasks", f"curl '{API_BASE_URL}/high-priority'")
    ]
    
    for description, command in examples:
        print_colored(f"\n# {description}", Colors.YELLOW)
        print(command)

def main():
    """Main function"""
    print_colored("🎯 Task Manager - Sample Data Population Script", Colors.BOLD)
    print_colored("=" * 50, Colors.BLUE)
    
    if len(sys.argv) > 1:
        command = sys.argv[1].lower()
        
        if command == "stats":
            if check_api_health():
                get_statistics()
            return
        elif command == "examples":
            show_sample_api_calls()
            return
        elif command == "help":
            print_colored("\nUsage:", Colors.BLUE)
            print("  python populate_data.py [command]")
            print("\nCommands:")
            print("  populate  - Populate database with sample data (default)")
            print("  stats     - Show current task statistics")
            print("  examples  - Show sample API calls")
            print("  help      - Show this help message")
            return
        elif command != "populate":
            print_colored(f"Unknown command: {command}", Colors.RED)
            print_colored("Use 'python populate_data.py help' for usage information", Colors.YELLOW)
            return
    
    # Default action: populate data
    if not check_api_health():
        return
    
    populate_tasks()
    get_statistics()
    show_sample_api_calls()
    
    print_colored("\n🎉 Data population completed!", Colors.GREEN)
    print_colored("You can now test the API endpoints or view data in H2 console:", Colors.BLUE)
    print_colored("http://localhost:8080/h2-console", Colors.YELLOW)

if __name__ == "__main__":
    main()
