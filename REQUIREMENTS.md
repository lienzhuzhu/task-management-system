# Task Management System - Take-Home Assignment

## Overview

Build a simple task management system where users can create and manage tasks. This project tests your ability to **follow existing code patterns** and implement features in a full-stack application.

**Time Estimate:** 2-3 hours

## Evaluation Criteria

We will evaluate:
- ✅ **Pattern Following**: How well you replicated the User module pattern
- ✅ **Code Quality**: Clean, readable, well-organized code
- ✅ **Functionality**: All CRUD operations work correctly
- ✅ **Pagination**: Proper implementation with navigation controls
- ✅ **Filtering**: Status filter works correctly
- ✅ **Error Handling**: Proper error messages and validation
- ✅ **UI/UX**: Clean interface following the User pages style
- ✅ **Understanding**: Can you explain your implementation decisions?

>_Note: The use of AI is permitted/welcomed, but you must be able to explain every technical aspect of your solution. We are more interested in critical thinking and problem-solving, not just the solutions themselves_

## What's Already Provided

To help you succeed, we've provided a **complete working reference implementation** of User management:

### Backend (Complete User Module) ✅
- `User.java` and `Task.java` entities (JPA models)
- `UserRepository.java` - Repository with pagination and filtering
- `UserDTO.java` - Data transfer objects (CreateUserDTO, UserResponseDTO)
- `UserService.java` - Business logic with pagination and name filter
- `UserController.java` - REST API endpoints with pagination
- `GlobalExceptionHandler.java` - Centralized error handling
- `ResourceNotFoundException.java` - Custom exception

### Frontend (Complete User Pages) ✅
- User list page with pagination controls
- User list page with name filter
- Create user form
- Edit user form
- Reusable Pagination component
- Dashboard showing user count

**Your Task:** Study how the User module works, then implement the Task module following the same pattern.

## What You Need to Implement

- Task list page
   - Display all tasks in a table/list
   - Show: title, assigned user name, status, priority, due date
   - Pagination controls (Previous, Next, page numbers)
   - Status filter dropdown (ALL, TODO, IN_PROGRESS, DONE)
   - Buttons to create, edit, delete tasks

- Create and Update task form
   - Form fields: title, description, status, priority, due date, assigned user
   - Submit to API
   - Navigate to task list on success
   - Display any error messages.

- Dashboard
   - Add total task count

## Testing Your Solution

1. Start backend: `cd backend && ./gradlew bootRun`
2. Start frontend: `cd frontend && npm run dev`
3. Navigate to http://localhost:3000
4. Test all functionality:
   - Create users and tasks
   - Test pagination (should have 15 tasks in seed data)
   - Test status filter dropdown
   - Edit and delete tasks
   - Check error handling (try invalid data)

## Submission Guidelines

1. Create a `.zip` file of the entire project directory
2. Send the zip file to your interview coordinator
3. **Do not include:**
   - `node_modules/` folder
   - `backend/build/` folder
   - `backend/.gradle/` folder
   - Any IDE-specific folders (`.idea/`, `.vscode/`, etc.)

> Or commit your changes to a git repository and share the link.

## Questions?

If you have any questions about the requirements or get stuck, please reach out to your interview coordinator.

## Tips for Success

- **Don't reinvent the wheel** - The User module is your guide
- **Copy and adapt** - It's okay to copy User code and modify for Task
- **The use of AI is permitted/welcomed** - But you must be able to explain every technical aspect of your solution.
- **Test frequently** - Run the app and test as you build
- **Read error messages** - They usually tell you what's wrong
- **Take breaks** - Step away if you get stuck
- **Ask questions** - We're here to help!

Good luck! 🚀
