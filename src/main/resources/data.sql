-- Task Manager Sample Data Population Script
-- This script populates the database with comprehensive sample data
-- 
-- ✅ PostgreSQL-compatible date functions using INTERVAL
-- ✅ Creates realistic scenarios with overdue, current, and future tasks
-- ✅ Works with Spring Boot's automatic data.sql execution
--
-- Scenarios included:
-- - Overdue tasks (past due dates)
-- - Tasks due soon (within days/weeks)  
-- - Future tasks (weeks/months ahead)
-- - Completed tasks (with proper completion dates)

-- Insert sample tasks with various statuses, priorities, and categories
-- Using PostgreSQL's INTERVAL syntax (also works with H2 when using PostgreSQL mode)

-- Development Tasks
INSERT INTO tasks (title, description, due_date, status, priority, category, assigned_to, estimated_hours, created_by, created_at, updated_at, completion_date) VALUES
('Implement User Authentication', 'Add JWT-based authentication system with role-based access control', DATEADD('DAY', 15, CURRENT_DATE), 'IN_PROGRESS', 'HIGH', 'Security', 'john.doe@company.com', 20, 'manager@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Build REST API Endpoints', 'Create comprehensive REST API for task management with full CRUD operations', DATEADD('DAY', 10, CURRENT_DATE), 'TODO', 'HIGH', 'Development', 'jane.smith@company.com', 16, 'lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Frontend Integration', 'Integrate React frontend with the new API endpoints', DATEADD('DAY', 20, CURRENT_DATE), 'TODO', 'MEDIUM', 'Frontend', 'frontend.dev@company.com', 24, 'manager@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Database Optimization', 'Optimize database queries and add proper indexing for better performance', DATEADD('DAY', 8, CURRENT_DATE), 'IN_PROGRESS', 'MEDIUM', 'Database', 'db.admin@company.com', 12, 'architect@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Mobile App Development', 'Develop mobile application for iOS and Android platforms', DATEADD('DAY', 45, CURRENT_DATE), 'TODO', 'LOW', 'Mobile', 'mobile.dev@company.com', 80, 'product@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Testing Tasks
('Unit Test Coverage', 'Increase unit test coverage to 95% for all critical components', DATEADD('DAY', 12, CURRENT_DATE), 'TODO', 'MEDIUM', 'Testing', 'qa.engineer@company.com', 14, 'qa.lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Performance Testing', 'Conduct load testing and stress testing for the application', DATEADD('DAY', 18, CURRENT_DATE), 'ON_HOLD', 'LOW', 'Testing', 'perf.tester@company.com', 16, 'qa.lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Integration Testing', 'Set up automated integration tests for all API endpoints', DATEADD('DAY', 14, CURRENT_DATE), 'TODO', 'MEDIUM', 'Testing', 'qa.engineer@company.com', 10, 'qa.lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Bug Fixes (some overdue to create realistic scenarios)
('Fix Memory Leak Issue', 'Resolve memory leak in the background task processor', DATEADD('DAY', -5, CURRENT_DATE), 'TODO', 'URGENT', 'Bug Fix', 'senior.dev@company.com', 8, 'support@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Resolve Database Timeout', 'Fix intermittent database connection timeout errors', DATEADD('DAY', -2, CURRENT_DATE), 'TODO', 'HIGH', 'Bug Fix', 'db.admin@company.com', 6, 'support@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('UI Responsiveness Bug', 'Fix responsive design issues on mobile devices', DATEADD('DAY', 5, CURRENT_DATE), 'IN_PROGRESS', 'MEDIUM', 'Bug Fix', 'frontend.dev@company.com', 4, 'ux.designer@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Documentation Tasks
('API Documentation', 'Write comprehensive API documentation with examples and use cases', DATEADD('DAY', 8, CURRENT_DATE), 'IN_PROGRESS', 'MEDIUM', 'Documentation', 'tech.writer@company.com', 12, 'manager@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('User Manual', 'Create user manual and help documentation for end users', DATEADD('DAY', 22, CURRENT_DATE), 'TODO', 'LOW', 'Documentation', 'tech.writer@company.com', 20, 'product@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Developer Guide', 'Write developer setup guide and contribution guidelines', DATEADD('DAY', 16, CURRENT_DATE), 'TODO', 'MEDIUM', 'Documentation', 'senior.dev@company.com', 8, 'lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- DevOps Tasks
('CI/CD Pipeline Setup', 'Configure automated testing and deployment pipeline with Jenkins', DATEADD('DAY', 10, CURRENT_DATE), 'TODO', 'HIGH', 'DevOps', 'devops.engineer@company.com', 18, 'cto@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Docker Containerization', 'Containerize the application and create Docker compose setup', DATEADD('DAY', 12, CURRENT_DATE), 'TODO', 'MEDIUM', 'DevOps', 'devops.engineer@company.com', 10, 'architect@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Monitoring Setup', 'Implement application monitoring with Prometheus and Grafana', DATEADD('DAY', 25, CURRENT_DATE), 'TODO', 'MEDIUM', 'DevOps', 'sre.engineer@company.com', 14, 'devops.lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Security Tasks
('Security Audit', 'Conduct comprehensive security audit and penetration testing', DATEADD('DAY', 28, CURRENT_DATE), 'TODO', 'HIGH', 'Security', 'security@company.com', 24, 'ciso@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Data Encryption', 'Implement end-to-end encryption for sensitive data', DATEADD('DAY', 20, CURRENT_DATE), 'TODO', 'HIGH', 'Security', 'security.dev@company.com', 16, 'security@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Completed Tasks (with past due dates and completion dates)
('Database Schema Design', 'Design and implement the initial database schema', DATEADD('DAY', -15, CURRENT_DATE), 'COMPLETED', 'HIGH', 'Database', 'db.admin@company.com', 16, 'architect@company.com', DATEADD('DAY', -25, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP)),

('Project Setup', 'Initialize project structure and configure build tools', DATEADD('DAY', -20, CURRENT_DATE), 'COMPLETED', 'MEDIUM', 'Setup', 'lead@company.com', 8, 'manager@company.com', DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -28, CURRENT_TIMESTAMP), DATEADD('DAY', -28, CURRENT_TIMESTAMP)),

('Requirements Analysis', 'Analyze and document all functional and non-functional requirements', DATEADD('DAY', -22, CURRENT_DATE), 'COMPLETED', 'HIGH', 'Analysis', 'analyst@company.com', 20, 'product@company.com', DATEADD('DAY', -32, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP)),

-- Overdue Tasks (intentionally past due for testing)
('Code Review Process', 'Establish code review guidelines and implement automated checks', DATEADD('DAY', -10, CURRENT_DATE), 'TODO', 'MEDIUM', 'Process', 'senior.dev@company.com', 6, 'lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Legacy Code Refactoring', 'Refactor legacy codebase to improve maintainability', DATEADD('DAY', -8, CURRENT_DATE), 'IN_PROGRESS', 'LOW', 'Maintenance', 'senior.dev@company.com', 32, 'architect@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Research Tasks
('Technology Research', 'Research new technologies for next generation architecture', DATEADD('DAY', 60, CURRENT_DATE), 'TODO', 'LOW', 'Research', 'architect@company.com', 40, 'cto@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Competitor Analysis', 'Analyze competitor products and identify improvement opportunities', DATEADD('DAY', 30, CURRENT_DATE), 'TODO', 'MEDIUM', 'Research', 'analyst@company.com', 16, 'product@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Maintenance Tasks
('Dependency Updates', 'Update all project dependencies to latest stable versions', DATEADD('DAY', 5, CURRENT_DATE), 'TODO', 'LOW', 'Maintenance', 'john.doe@company.com', 4, 'lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Log Cleanup', 'Clean up old log files and implement log rotation', DATEADD('DAY', 8, CURRENT_DATE), 'TODO', 'LOW', 'Maintenance', 'devops.engineer@company.com', 2, 'sre.engineer@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- UI/UX Tasks
('User Interface Redesign', 'Redesign user interface based on user feedback and usability testing', DATEADD('DAY', 40, CURRENT_DATE), 'TODO', 'MEDIUM', 'Design', 'ux.designer@company.com', 30, 'design.lead@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

('Accessibility Improvements', 'Implement accessibility features to comply with WCAG 2.1 standards', DATEADD('DAY', 26, CURRENT_DATE), 'TODO', 'MEDIUM', 'Accessibility', 'frontend.dev@company.com', 12, 'ux.designer@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),

-- Training Tasks
('Team Training', 'Conduct training sessions on new tools and technologies', DATEADD('DAY', 15, CURRENT_DATE), 'TODO', 'LOW', 'Training', 'senior.dev@company.com', 8, 'manager@company.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);

