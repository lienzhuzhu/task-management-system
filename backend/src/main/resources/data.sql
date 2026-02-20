-- Sample Data for Task Management System
-- Spring Boot will automatically execute this on startup
-- Using MERGE instead of INSERT to make the script idempotent (safe to run multiple times)

-- Sample Users
MERGE INTO users (id, name, email, created_at) KEY(id) VALUES
(1, 'Alice Johnson', 'alice.johnson@example.com', CURRENT_TIMESTAMP),
(2, 'Bob Smith', 'bob.smith@example.com', CURRENT_TIMESTAMP),
(3, 'Carol Williams', 'carol.williams@example.com', CURRENT_TIMESTAMP),
(4, 'David Brown', 'david.brown@example.com', CURRENT_TIMESTAMP);

-- Sample Tasks
-- Using individual MERGE statements since H2 doesn't support multi-row MERGE with auto-increment IDs
-- Alice's tasks
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(1, 'Setup development environment', 'Install all required tools and dependencies', 'DONE', 'HIGH', '2024-11-15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(2, 'Review pull requests', 'Review and approve pending PRs from team', 'IN_PROGRESS', 'HIGH', '2024-12-05', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(3, 'Update documentation', 'Update API documentation for new endpoints', 'TODO', 'MEDIUM', '2024-12-10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(4, 'Fix login bug', 'Users report issues with login on mobile', 'IN_PROGRESS', 'HIGH', '2024-12-03', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Bob's tasks
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(5, 'Write unit tests', 'Add unit tests for user service layer', 'TODO', 'HIGH', '2024-12-08', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(6, 'Database migration', 'Create migration script for new schema changes', 'TODO', 'MEDIUM', '2024-12-12', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(7, 'Code review training', 'Complete code review best practices course', 'DONE', 'LOW', '2024-11-20', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(8, 'Refactor authentication module', 'Improve auth code structure and add comments', 'IN_PROGRESS', 'MEDIUM', '2024-12-06', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Carol's tasks
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(9, 'Design new landing page', 'Create mockups for homepage redesign', 'DONE', 'HIGH', '2024-11-25', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(10, 'Implement search feature', 'Add search functionality to product catalog', 'IN_PROGRESS', 'HIGH', '2024-12-04', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(11, 'Optimize images', 'Compress and optimize images for faster loading', 'TODO', 'LOW', '2024-12-15', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- David's tasks
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(12, 'Deploy to staging', 'Deploy latest changes to staging environment', 'TODO', 'HIGH', '2024-12-02', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(13, 'Monitor production metrics', 'Check application performance and error rates', 'DONE', 'MEDIUM', '2024-11-28', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(14, 'Setup CI/CD pipeline', 'Configure automated testing and deployment', 'IN_PROGRESS', 'HIGH', '2024-12-07', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO tasks (id, title, description, status, priority, due_date, user_id, created_at, updated_at) KEY(id) VALUES
(15, 'Security audit', 'Review application for security vulnerabilities', 'TODO', 'HIGH', '2024-12-20', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Reset auto-increment sequences to prevent primary key conflicts
-- After inserting explicit IDs (1-4 for users, 1-15 for tasks), we need to set the next ID
ALTER TABLE users ALTER COLUMN id RESTART WITH 5;
ALTER TABLE tasks ALTER COLUMN id RESTART WITH 16;

