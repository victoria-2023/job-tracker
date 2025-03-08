# Job Application Tracker

A full-stack application to help users track their job applications with a Spring Boot backend (Java 21) and React frontend.

## Project Structure

```
job-tracker/              # Spring Boot backend
├── src/
│   ├── main/
│   └── test/
├── pom.xml
└── job-tracker-frontend/ # React frontend
    ├── src/
    ├── package.json
    └── vite.config.ts
```

## Quick Start

### Backend (Spring Boot)
1. From the root directory:
```bash
mvn clean install
mvn spring-boot:run
```
The backend will start on `http://localhost:8080`

### Frontend (React)
1. Navigate to frontend directory:
```bash
cd job-tracker-frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```
The frontend will start on `http://localhost:5173`

## Documentation
- Backend API documentation is available at `http://localhost:8080/swagger-ui/index.html`
- Detailed backend documentation can be found below
- Frontend documentation is available in `job-tracker-frontend/README.md`

## Backend Documentation

### Technology Stack

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (can be configured for PostgreSQL)
- Spring Security
- OpenAPI (Swagger) for documentation

### API Endpoints

#### Jobs

- `GET /api/jobs` - Get all jobs
- `GET /api/jobs/{id}` - Get job by ID
- `GET /api/jobs/status/{status}` - Get jobs by status
- `POST /api/jobs` - Create new job
- `PUT /api/jobs` - Update existing job
- `DELETE /api/jobs/{id}` - Delete job

### Data Models

#### Job
```json
{
  "id": "long",
  "company": "string",
  "position": "string",
  "location": "string",
  "status": "string (APPLIED, INTERVIEWING, ACCEPTED, REJECTED)",
  "applicationDate": "datetime",
  "createdAt": "datetime",
  "createdBy": "string",
  "modifiedAt": "datetime",
  "modifiedBy": "string"
}
```

### Setup & Development

1. Requirements:
   - Java 21+
   - Maven

2. Build:
```bash
mvn clean install
```

3. Run:
```bash
mvn spring-boot:run
```

4. Access:
   - API: `http://localhost:8080/api`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
   - API Docs: `http://localhost:8080/v3/api-docs`

### Testing

Run tests using:
```bash
mvn test
```

Tests include:
- Controller tests (MockMvc)
- Service layer tests
- Repository integration tests 