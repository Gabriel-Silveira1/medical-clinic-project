# Medical Clinic System — REST API

A RESTful web service built with Spring Boot and JPA/Hibernate that models the core operations of a medical clinic: patients, doctors, specialties, appointments, consultations and payments. The project follows a classic layered architecture (Resource → Service → Repository) and covers the full development cycle — domain modelling, database configuration, CRUD operations and structured exception handling.

---

## Table of Contents

1. [Tech Stack](#1-tech-stack)
2. [Architecture](#2-architecture)
3. [Domain Model](#3-domain-model)
4. [Project Structure](#4-project-structure)
5. [Prerequisites](#5-prerequisites)
6. [Configuration & Profiles](#6-configuration--profiles)
7. [Running the Application](#7-running-the-application)
8. [REST API](#8-rest-api)
9. [Error Handling](#9-error-handling)
10. [Database Seeding](#10-database-seeding)
11. [Roadmap / Possible Improvements](#11-roadmap--possible-improvements)

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

- **Resource** — `@RestController` classes that expose HTTP endpoints, parse input and return `ResponseEntity`. They contain no business logic.
- **Service** — `@Service` classes that orchestrate business rules and translate persistence errors into domain exceptions.
- **Repository** — Spring Data JPA interfaces extending `JpaRepository`, responsible solely for database access.
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
    │   ├── repositories/                    # Spring Data JPA interfaces
    │   ├── services/                        # Business logic
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

## 8. REST API

Base URL: `http://localhost:8080`

### Patients — `/patients` (full CRUD)

| Method | Path              | Description             | Status           |
|--------|-------------------|-------------------------|------------------|
| GET    | `/patients`       | List all patients        | `200 OK`         |
| GET    | `/patients/{id}`  | Find patient by id       | `200 OK`         |
| POST   | `/patients`       | Create a new patient     | `201 Created`    |
| PUT    | `/patients/{id}`  | Update an existing patient | `200 OK`       |
| DELETE | `/patients/{id}`  | Delete a patient         | `204 No Content` |

### Doctors — `/doctors` (read)

| Method | Path            | Description        | Status   |
|--------|-----------------|--------------------|----------|
| GET    | `/doctors`      | List all doctors   | `200 OK` |
| GET    | `/doctors/{id}` | Find doctor by id  | `200 OK` |

### Specialties — `/specialties` (read)

| Method | Path                 | Description            | Status   |
|--------|----------------------|------------------------|----------|
| GET    | `/specialties`       | List all specialties   | `200 OK` |
| GET    | `/specialties/{id}`  | Find specialty by id   | `200 OK` |

### Appointments — `/appointments` (read)

| Method | Path                  | Description             | Status   |
|--------|-----------------------|-------------------------|----------|
| GET    | `/appointments`       | List all appointments   | `200 OK` |
| GET    | `/appointments/{id}`  | Find appointment by id  | `200 OK` |

### Consultations — `/consultations` (read)

| Method | Path                   | Description              | Status   |
|--------|------------------------|--------------------------|----------|
| GET    | `/consultations`       | List all consultations   | `200 OK` |
| GET    | `/consultations/{id}`  | Find consultation by id  | `200 OK` |

### Payments — `/payments` (read)

| Method | Path             | Description         | Status   |
|--------|------------------|---------------------|----------|
| GET    | `/payments`      | List all payments   | `200 OK` |
| GET    | `/payments/{id}` | Find payment by id  | `200 OK` |

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
  "cpf": "999.888.777-66",
  "email": "carlos@gmail.com",
  "phone": "11977770001",
  "birthDate": "1992-07-10"
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

## 9. Error Handling

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

| Exception                   | HTTP Status         | Trigger                                                        |
|-----------------------------|---------------------|----------------------------------------------------------------|
| `ResourceNotFoundException` | `404 Not Found`     | `findById`, `update` or `delete` with a non-existent id        |
| `DatabaseException`         | `400 Bad Request`   | Referential integrity violation (e.g. deleting a patient with appointments) |

---

## 10. Database Seeding

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

## 11. Roadmap / Possible Improvements

- **Validation** — apply `@Valid` and Bean Validation annotations on request payloads
- **DTOs** — decouple HTTP contracts from JPA entities using input/output DTOs
- **Full CRUD** — extend CRUD operations to Doctor, Specialty, Appointment and Consultation endpoints
- **Security** — add authentication and authorization with Spring Security + JWT
- **PostgreSQL deploy** — configure production profile and deploy to a cloud provider
- **Pagination** — return `Page<T>` from list endpoints instead of unbounded `List<T>`
- **OpenAPI** — publish a generated API spec with springdoc-openapi
- **Test coverage** — add unit tests for services and slice tests (`@WebMvcTest`, `@DataJpaTest`)
