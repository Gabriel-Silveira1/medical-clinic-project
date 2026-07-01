# Medical Clinic System — REST API

A RESTful web service built with Spring Boot and JPA/Hibernate that models the core operations of a medical clinic: patients, doctors, specialties, appointments, consultations and payments. The project follows a classic layered architecture (Resource → Service → Repository) and covers the full development cycle — domain modelling, database configuration, CRUD operations, structured exception handling and DTO-based data transfer.

---

## Table of Contents

1. [Tech Stack](#1-tech-stack)
2. [Architecture](#2-architecture)
3. [Domain Model](#3-domain-model)
4. [Project Structure](#4-project-structure)
5. [Prerequisites](#5-prerequisites)
6. [Configuration & Profiles](#6-configuration--profiles)
7. [Running the Application](#7-running-the-application)
8. [Running with Docker](#8-running-with-docker)
9. [REST API](#9-rest-api)
10. [Error Handling](#10-error-handling)
11. [Database Seeding](#11-database-seeding)
12. [Roadmap / Possible Improvements](#12-roadmap--possible-improvements)

---

## 1. Tech Stack

| Layer            | Technology                                      |
|------------------|-------------------------------------------------|
| Language         | Java 21                                         |
| Framework        | Spring Boot 4.x (Web MVC, Data JPA)             |
| Persistence      | Hibernate / Jakarta Persistence (JPA)           |
| Database (test)  | H2 (in-memory)                                  |
| Database (prod)  | PostgreSQL                                      |
| Build Tool       | Maven                                           |
| Containerization | Docker (multi-stage build)                      |
| JSON             | Jackson                                         |

---

## 2. Architecture

The codebase follows a three-tier layered architecture, keeping each layer focused on a single responsibility:

```
HTTP Request
     |
     v
+------------------+    delegates    +-----------------+    persists    +-------------------+
| Resource (REST)  | --------------> | Service (rules) | -------------> | Repository (JPA)  |
+------------------+                 +-----------------+                +-------------------+
        ^                                                                         |
        |                                                                         v
        +-------------- @ControllerAdvice <--- domain exception ---           Database
```

- **Resource** — `@RestController` classes that expose HTTP endpoints, parse input via `RequestDTO` and return `ResponseDTO` wrapped in `ResponseEntity`. They contain no business logic.
- **Service** — `@Service` classes that orchestrate business rules, convert between DTOs and entities, and translate persistence errors into domain exceptions.
- **Repository** — Spring Data JPA interfaces extending `JpaRepository`, responsible solely for database access.
- **DTOs** — `RequestDTO` classes define what data enters the API; `ResponseDTO` classes define what data leaves, keeping JPA entities internal and preventing over-exposure of sensitive fields.
- **Domain exceptions** — `services/exceptions/` (`ResourceNotFoundException`, `DatabaseException`) translated to HTTP responses by `ResourceExceptionHandler` (`@ControllerAdvice`).

---

## 3. Domain Model

The domain models a medical clinic flow where a `Patient` schedules `Appointment`s with a `Doctor` of a given `Specialty`. A completed `Appointment` generates a `Consultation` with diagnosis and prescription, which may then have an associated `Payment`.

```
Specialty 1 ---* Doctor 1 ---* Appointment *--- 1 Patient
                                    |
                                    | 1
                                    v
                              Consultation
                                    |
                                    | 1
                                    v
                                 Payment
```

### Entities

| Entity               | Notes                                                                          |
|----------------------|--------------------------------------------------------------------------------|
| `Patient`            | Person seeking medical care; associated with multiple appointments.            |
| `Doctor`             | Medical professional; belongs to a specialty and has multiple appointments.     |
| `Specialty`          | Medical specialty (e.g. Cardiology); groups doctors.                           |
| `Appointment`        | Scheduled visit linking a patient and a doctor; holds moment and status.       |
| `Consultation`       | Result of a completed appointment; contains diagnosis and prescription.        |
| `Payment`            | Financial record of a consultation; one-to-one with `Consultation`.            |
| `AppointmentStatus`  | Enum: `SCHEDULED`, `CONFIRMED`, `COMPLETED`, `CANCELLED`.                      |

### Relationships

| Relationship                     | Type         |
|----------------------------------|--------------|
| `Specialty` → `Doctor`           | One-to-Many  |
| `Doctor` → `Appointment`         | One-to-Many  |
| `Patient` → `Appointment`        | One-to-Many  |
| `Appointment` → `Consultation`   | One-to-One   |
| `Consultation` → `Payment`       | One-to-One   |

---

## 4. Project Structure

```
src/
└── main/
    ├── java/com/gabrielsilveira/clinica/
    │   ├── ClinicaApplication.java          # Spring Boot entry point
    │   ├── config/
    │   │   └── TestConfig.java              # Seed data (profile: test)
    │   ├── entities/
    │   │   ├── Patient.java
    │   │   ├── Doctor.java
    │   │   ├── Specialty.java
    │   │   ├── Appointment.java
    │   │   ├── Consultation.java
    │   │   ├── Payment.java
    │   │   └── AppointmentStatus.java       # Enum with integer code mapping
    │   ├── dto/
    │   │   ├── PatientRequestDTO.java
    │   │   ├── PatientResponseDTO.java
    │   │   ├── DoctorRequestDTO.java
    │   │   ├── DoctorResponseDTO.java
    │   │   ├── SpecialtyRequestDTO.java
    │   │   ├── SpecialtyResponseDTO.java
    │   │   ├── AppointmentRequestDTO.java
    │   │   ├── AppointmentResponseDTO.java
    │   │   ├── ConsultationRequestDTO.java
    │   │   ├── ConsultationResponseDTO.java
    │   │   ├── PaymentRequestDTO.java
    │   │   └── PaymentResponseDTO.java
    │   ├── repositories/                    # Spring Data JPA interfaces
    │   ├── services/                        # Business logic + DTO conversion
    │   │   └── exceptions/                  # Domain exceptions
    │   └── resources/                       # REST controllers
    │       └── exceptions/                  # ControllerAdvice + StandardError
    └── resources/
        ├── application.properties           # Active profile, generic config
        └── application-test.properties      # H2 in-memory (test)
```

---

## 5. Prerequisites

- **JDK 21+**
- **Maven 3.8+** (or use the Maven Wrapper included in the project)
- For the production profile only: **PostgreSQL 12+** with a database named `clinic_db` on `localhost:5432`
- For containerized execution: **Docker** installed and running

---

## 6. Configuration & Profiles

The active profile is selected in `application.properties`:

```properties
spring.application.name=clinica
spring.profiles.active=test
spring.jpa.open-in-view=true
```

### `test` profile — H2 in-memory

File: `application-test.properties`

```properties
# DATASOURCE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# JPA, SQL
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

The test profile uses an in-memory H2 database — no external setup required. All data is populated on startup via `TestConfig` and is lost when the application stops.

### `prod` profile — PostgreSQL

For production, create an `application-prod.properties` with your PostgreSQL credentials and set `spring.profiles.active=prod`.

---

## 7. Running the Application

```bash
# Clone the repository
git clone git@github.com:Gabriel-Silveira1/medical-clinic-project.git
cd medical-clinic-project

# Run with H2 in-memory (no external database required)
./mvnw spring-boot:run

# Or with Maven installed globally
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

On startup (test profile), seed data is automatically inserted: 2 patients, 3 specialties, 3 doctors, 3 appointments, 1 consultation and 1 payment.

---

## 8. Running with Docker

The application is fully containerized with a multi-stage `Dockerfile`, and the image is published on Docker Hub. The build compiles the project with Maven in a first stage and copies only the resulting `.jar` into a lightweight JRE image for runtime, keeping the final image small.

### Option 1 — Pull the image from Docker Hub

```bash
docker pull gabrielsilveira1/clinica-medica:latest
docker run -p 8080:8080 gabrielsilveira1/clinica-medica:latest
```

### Option 2 — Build the image locally

```bash
docker build -t clinica-medica .
docker run -p 8080:8080 clinica-medica
```

The API will be available at `http://localhost:8080`.

The container runs with the `test` profile (H2 in-memory database), so no external database is required. Seed data is populated automatically on startup.

**Docker Hub:** [gabrielsilveira1/clinica-medica](https://hub.docker.com/r/gabrielsilveira1/clinica-medica)

---

## 9. REST API

Base URL: `http://localhost:8080`

### Patients — `/patients` (full CRUD)

| Method | Path              | Description               | Status           |
|--------|-------------------|---------------------------|------------------|
| GET    | `/patients`       | List all patients          | `200 OK`         |
| GET    | `/patients/{id}`  | Find patient by id         | `200 OK`         |
| POST   | `/patients`       | Create a new patient       | `201 Created`    |
| PUT    | `/patients/{id}`  | Update an existing patient | `200 OK`         |
| DELETE | `/patients/{id}`  | Delete a patient           | `204 No Content` |

### Doctors — `/doctors` (full CRUD)

| Method | Path             | Description                | Status           |
|--------|------------------|----------------------------|------------------|
| GET    | `/doctors`       | List all doctors           | `200 OK`         |
| GET    | `/doctors/{id}`  | Find doctor by id          | `200 OK`         |
| POST   | `/doctors`       | Create a new doctor        | `201 Created`    |
| PUT    | `/doctors/{id}`  | Update an existing doctor  | `200 OK`         |
| DELETE | `/doctors/{id}`  | Delete a doctor            | `204 No Content` |

### Specialties — `/specialties` (full CRUD)

| Method | Path                  | Description                   | Status           |
|--------|-----------------------|-------------------------------|------------------|
| GET    | `/specialties`        | List all specialties          | `200 OK`         |
| GET    | `/specialties/{id}`   | Find specialty by id          | `200 OK`         |
| POST   | `/specialties`        | Create a new specialty        | `201 Created`    |
| PUT    | `/specialties/{id}`   | Update an existing specialty  | `200 OK`         |
| DELETE | `/specialties/{id}`   | Delete a specialty            | `204 No Content` |

### Appointments — `/appointments` (full CRUD)

| Method | Path                    | Description                     | Status           |
|--------|-------------------------|---------------------------------|------------------|
| GET    | `/appointments`         | List all appointments           | `200 OK`         |
| GET    | `/appointments/{id}`    | Find appointment by id          | `200 OK`         |
| POST   | `/appointments`         | Create a new appointment        | `201 Created`    |
| PUT    | `/appointments/{id}`    | Update an existing appointment  | `200 OK`         |
| DELETE | `/appointments/{id}`    | Delete an appointment           | `204 No Content` |

### Consultations — `/consultations` (full CRUD)

| Method | Path                    | Description                      | Status           |
|--------|-------------------------|----------------------------------|------------------|
| GET    | `/consultations`        | List all consultations           | `200 OK`         |
| GET    | `/consultations/{id}`   | Find consultation by id          | `200 OK`         |
| POST   | `/consultations`        | Create a new consultation        | `201 Created`    |
| PUT    | `/consultations/{id}`   | Update an existing consultation  | `200 OK`         |
| DELETE | `/consultations/{id}`   | Delete a consultation            | `204 No Content` |

### Payments — `/payments` (full CRUD)

| Method | Path              | Description               | Status           |
|--------|-------------------|---------------------------|------------------|
| GET    | `/payments`       | List all payments         | `200 OK`         |
| GET    | `/payments/{id}`  | Find payment by id        | `200 OK`         |
| POST   | `/payments`       | Create a new payment      | `201 Created`    |
| PUT    | `/payments/{id}`  | Update an existing payment| `200 OK`         |
| DELETE | `/payments/{id}`  | Delete a payment          | `204 No Content` |

### Example — create patient

```http
POST /patients
Content-Type: application/json

{
  "name": "Carlos Mendes",
  "cpf": "999.888.777-66",
  "email": "carlos@gmail.com",
  "phone": "11977770001",
  "birthDate": "1992-07-10"
}
```

Response:

```http
HTTP/1.1 201 Created
Location: http://localhost:8080/patients/3
Content-Type: application/json

{
  "id": 3,
  "name": "Carlos Mendes",
  "email": "carlos@gmail.com",
  "phone": "11977770001",
  "birthDate": "1992-07-10"
}
```

> Note: `cpf` is accepted on input but intentionally omitted from the response — sensitive fields are protected via dedicated `ResponseDTO` classes.

### Example — create doctor

```http
POST /doctors
Content-Type: application/json

{
  "name": "Dr. Ricardo Alves",
  "crm": "CRM-SP 11111",
  "email": "ricardo@clinic.com",
  "phone": "11999990004",
  "specialtyId": 1
}
```

Response:

```http
HTTP/1.1 201 Created
Location: http://localhost:8080/doctors/4
Content-Type: application/json

{
  "id": 4,
  "name": "Dr. Ricardo Alves",
  "crm": "CRM-SP 11111",
  "email": "ricardo@clinic.com",
  "phone": "11999990004",
  "specialtyName": "Cardiology"
}
```

### Example — update patient

```http
PUT /patients/3
Content-Type: application/json

{
  "name": "Carlos Mendes",
  "email": "carlos.novo@gmail.com",
  "phone": "11977770002"
}
```

> Note: `cpf` and `birthDate` are intentionally excluded from updates — these fields are immutable after registration.

---

## 10. Error Handling

All domain exceptions are intercepted by `ResourceExceptionHandler` (`@ControllerAdvice`) and returned as a consistent JSON payload.

### `StandardError` payload

```json
{
  "timestamp": "2024-03-10T09:00:00Z",
  "status": 404,
  "error": "Resource not found",
  "message": "Resource not found. Id 99",
  "path": "/patients/99"
}
```

### Exception mapping

| Exception                   | HTTP Status       | Trigger                                                                     |
|-----------------------------|-------------------|-----------------------------------------------------------------------------|
| `ResourceNotFoundException` | `404 Not Found`   | `findById`, `update` or `delete` with a non-existent id                     |
| `DatabaseException`         | `400 Bad Request` | Referential integrity violation (e.g. deleting a patient with appointments) |

---

## 11. Database Seeding

When running under the `test` profile, `TestConfig` populates the H2 database on startup with the following data:

| Entity        | Records                                              |
|---------------|------------------------------------------------------|
| Patients      | João Silva, Fernanda Costa                           |
| Specialties   | Cardiology, Orthopedics, Dermatology                 |
| Doctors       | Dr. Carlos Lima, Dr. Ana Souza, Dr. Beatriz Nunes    |
| Appointments  | 3 appointments across different statuses             |
| Consultation  | 1 consultation with diagnosis and prescription       |
| Payment       | 1 payment linked to the consultation                 |

This allows you to immediately test all endpoints after startup without manual data entry.

---

## 12. Roadmap / Possible Improvements

- **Validation** — apply `@Valid` and Bean Validation annotations on request payloads
- **Security** — add authentication and authorization with Spring Security + JWT
- **PostgreSQL deploy** — configure production profile and deploy to a cloud provider
- **Pagination** — return `Page<T>` from list endpoints instead of unbounded `List<T>`
- **OpenAPI** — publish a generated API spec with springdoc-openapi
- **Test coverage** — add unit tests for services and slice tests (`@WebMvcTest`, `@DataJpaTest`)
