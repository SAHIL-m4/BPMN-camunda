# Leave Application System
React + TypeScript, Spring Boot 3.5 / Java 21, Camunda 8.9, H2, Docker.

## Project structure
- `bpmn/leave-request.bpmn` and `bpmn/leave-approval.form`: workflow assets
- `backend/`: REST API, persistence, Camunda client and job workers
- `frontend/`: employee and manager portal
- `docker/`: application Dockerfiles and Compose file

## Prerequisites
Java 21, Maven 3.9+, Node.js 20+, npm, Docker Desktop, and Camunda 8.9 Self-Managed.

## Run locally (Mac)
1. Start Camunda 8.9 using the official Camunda 8.9 Docker Compose distribution. Download/extract the complete distribution archive and run `docker compose up -d` from that extracted directory.
2. In terminal 1:
   `cd backend && mvn spring-boot:run`
3. In terminal 2:
   `cd frontend && npm install && npm run dev`
4. Open http://localhost:5173

Backend: http://localhost:8081
Camunda UI/API: http://localhost:8080 (depending on the selected distribution and authentication configuration)
H2 console: http://localhost:8081/h2-console

## Important setup notes
- Check the Camunda 8.9 distribution's authentication and REST/gRPC endpoints. Update `backend/src/main/resources/application.yml` to match.
- Deploy the BPMN and form from `bpmn/` with Camunda Modeler or the backend deployment annotation.
- The Java service uses the Camunda Java Client. If your Camunda 8.9 patch level exposes different client methods, align the client calls with that patch's API documentation.
- This is a learning MVP. Add login, role-based authorization, CSRF protections, audit logging and secure secrets before any real use.
- H2 is for local development. For longer-lived deployment, use PostgreSQL and persistent volumes.

## Docker
The `docker-compose.yml` in the project packages the application frontend, backend and PostgreSQL. It expects Camunda to be running separately on the Docker host. Set `CAMUNDA_REST_ADDRESS` and `CAMUNDA_GRPC_ADDRESS` for your environment before starting the application stack.
