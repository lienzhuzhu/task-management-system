# Task Management System - Take-Home Project

This is a take-home interview assignment where you'll build a Task Management System with user and task management features. The project includes a fully configured frontend and backend with database connectivity and **a complete User module as a reference implementation**.

## Tech Stack

### Frontend
- **Framework**: Nuxt 3 (Vue 3)
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **Testing**: Vitest
- **Port**: 3000

### Backend
- **Framework**: Spring Boot 3.5
- **Build Tool**: Gradle
- **Language**: Java 21
- **Database**: H2 (file-based)
- **ORM**: JPA/Hibernate
- **API Documentation**: OpenAPI/Swagger
- **Port**: 8080

## Architecture

This project uses a **development-focused architecture**:
- Frontend runs on **http://localhost:3000** with hot reload
- Backend runs on **http://localhost:8080**
- CORS is pre-configured for communication between services
- Both services run independently with their own dev servers

## Prerequisites

- Node.js 22+
- npm
- Java 21

## Getting Started

You need to run both services in separate terminals.

**Terminal 1 - Backend:**
```bash
cd backend
./gradlew bootRun
```

On Windows:
```bash
cd backend
gradlew.bat bootRun
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm install
npm run dev
```

**That's it!** The frontend will auto-reload on code changes. For backend changes, you'll need to restart the server (Ctrl+C, then `./gradlew bootRun` again).

**Access the application:**
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api/
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/interviewdb`
  - Username: `sa`
  - Password: (leave empty)
- Health Check: http://localhost:8080/actuator/health

Press `Ctrl+C` in each terminal to stop the services.

## Available Commands

### Backend Commands

From the `backend/` directory:

```bash
# Run application
./gradlew bootRun

# Run tests
./gradlew test

# Build JAR
./gradlew build

# Clean build artifacts
./gradlew clean
```

### Frontend Commands

From the `frontend/` directory:

```bash
# Install dependencies
npm install

# Run development server
npm run dev

# Run tests
npm test

# Build for production
npm run build

# Generate static site
npm run generate
```

## Verifying Your Setup

1. Start both backend and frontend (see Getting Started above)
2. Visit http://localhost:3000
3. Navigate to "Users" in the menu to see the complete reference implementation
4. Try creating, editing, and viewing users

This confirms:
- ✅ Backend is running on :8080
- ✅ Frontend is running on :3000
- ✅ CORS is properly configured
- ✅ API communication is working
- ✅ Database is initialized with seed data

## Development Tips

- **Hot Reload**: Frontend changes reflect instantly at http://localhost:3000 with HMR
- **Backend Restart**: Backend requires manual restart after Java changes (Ctrl+C, then re-run `./gradlew bootRun`)
- **Database**: H2 database is file-based and stored in `backend/data/`
- **Schema Management**: Database schema is auto-updated based on JPA entities
- **CORS**: Pre-configured to allow frontend (:3000) to call backend (:8080)
- **API Pattern**: All API endpoints are under `/api/*` prefix

---

## 📋 Assignment Requirements

**👉 Please see [REQUIREMENTS.md](REQUIREMENTS.md) for:**
- What's already provided (complete User module reference)
- What you need to implement (Task module)
- Evaluation criteria
- Testing instructions
- Submission guidelines

## Troubleshooting

### Port already in use
- **Backend (8080)**: Change in `backend/src/main/resources/application.yml`
- **Frontend (3000)**: Change in `frontend/nuxt.config.ts` or stop other Node processes

### Backend won't start
- Ensure Java 21 is installed: `java -version`
- Make sure port 8080 is free
- Check for errors in the console output
- Try: `cd backend && ./gradlew clean bootRun`

### Frontend won't start
- Ensure Node.js 22.19.0+ is installed: `node -version`
- Run `npm install` in the frontend directory
- Make sure port 3000 is free
- Try deleting `node_modules` and `.nuxt` folders and running `npm install` again

### Database issues
- Ensure `backend/data/` directory exists and is writable
- Check `application.yml` for correct database configuration
- Try deleting `backend/data/` and restarting to get a fresh database
- Seed data should load automatically from `data.sql`

### CORS errors
- Verify frontend is running on port 3000
- Verify backend is running on port 8080
- Check `CorsConfig.java` has the correct frontend URL

### Gradle issues
- Make sure `gradlew` is executable: `chmod +x gradlew` (Linux/Mac)
- Try: `./gradlew clean` to clean build artifacts
- Check Java version is 21
