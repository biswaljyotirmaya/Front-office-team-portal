# 2-Front-office-team-portal

## Description

`2-Front-office-team-portal` is a Spring Boot web application for managing student inquiries in a front-office or counseling workflow. It provides account onboarding for team members, login and password recovery flows, a dashboard with inquiry statistics, and inquiry management screens for creating, updating, filtering, and deleting records.

The project uses server-side rendering with Thymeleaf and persists data in MySQL using Spring Data JPA.

## Features

- User signup with temporary password generation
- Account unlock flow through email
- Login and logout using HTTP session
- Forgot password support via email
- Dashboard with total, enrolled, and lost inquiry counts
- Add new student inquiries
- Edit existing inquiries
- Delete inquiries
- Filter inquiries by course, status, and class mode
- Startup data seeding for courses and inquiry statuses

## Tech Stack

- Java 17
- Spring Boot 3.5.13
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL
- Spring Boot Validation
- Spring Boot Mail
- Lombok
- Maven

## Project Structure

```text
src/main/java/com/jb/fop
|- controller   # MVC controllers
|- dto          # Form and dashboard DTOs
|- entity       # JPA entities
|- repository   # Spring Data repositories
|- runner       # Startup data loader
|- service      # Business logic
|- utility      # Email and password helpers

src/main/resources
|- templates    # Thymeleaf views
|- static       # Static assets
|- application.properties
```

## Setup Instructions

### Prerequisites

- JDK 17
- Maven 3.9+ or the included Maven wrapper
- MySQL Server
- A valid SMTP account for sending emails

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd 2-Front-office-team-portal
```

### 2. Create the MySQL database

```sql
CREATE DATABASE fotp;
```

### 3. Configure application properties

Update `src/main/resources/application.properties` with your local database and mail credentials.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fotp
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Using the Maven wrapper on Windows:

```bash
.\mvnw.cmd spring-boot:run
```

### 5. Open the application

Visit:

```text
http://localhost:8080/
```

## Seed Data

On application startup, the `DataLoader` seeds the following reference data:

- Courses: `Java`, `Python`, `JavaScript`, `MySQL`, `C++`
- Inquiry statuses: `New`, `Enrolled`, `Lost`

Note: the current startup loader clears and recreates the course and status master data on every application start.

## API Endpoints

This project is primarily an MVC web application. Most endpoints return Thymeleaf views, and one endpoint returns JSON for inquiry filtering.

| Method | Endpoint | Description | Response Type |
|--------|----------|-------------|---------------|
| GET | `/` | Landing page | HTML |
| GET | `/login` | Show login form | HTML |
| POST | `/login` | Authenticate user and start session | Redirect |
| GET | `/signup` | Show signup form | HTML |
| POST | `/signup` | Register a new user and send unlock email | HTML |
| GET | `/unlock?email={email}` | Show unlock account form | HTML |
| POST | `/unlock` | Unlock account with temporary password | HTML |
| GET | `/forgotpass` | Show forgot password page | HTML |
| POST | `/forgotpass` | Send existing password to registered email | Redirect |
| GET | `/logout` | Invalidate session and logout user | Redirect |
| GET | `/dashboard` | Show inquiry summary for logged-in user | HTML |
| GET | `/addinquiry` | Show add inquiry form | HTML |
| POST | `/addinquiry` | Save a new inquiry | Redirect |
| GET | `/inquiries` | View inquiry list with optional filters | HTML |
| GET | `/inquiries/filter` | Filter inquiries by `course`, `status`, and `mode` | JSON |
| GET | `/edit/{id}` | Show edit form for an inquiry | HTML |
| POST | `/edit/{id}` | Update an existing inquiry | Redirect |
| GET | `/delete/{id}` | Delete an inquiry | Redirect |

### Filter Parameters

`/inquiries` and `/inquiries/filter` support these optional query parameters:

- `course`
- `status`
- `mode`

Example:

```text
GET /inquiries/filter?course=Java&status=Enrolled&mode=Online
```

## Inquiry Data Model

Each inquiry record includes:

- `studentName`
- `phoneNumber`
- `classMode`
- `courseName`
- `inquiryStatus`
- `createdDate`
- `updatedDate`

## Default Workflow

1. A front-office user signs up with name, email, and phone number.
2. The system generates a temporary password and sends an account unlock email.
3. The user unlocks the account by setting a new password.
4. The user logs in and accesses the dashboard.
5. The user creates, tracks, filters, edits, and deletes student inquiries.

## Build and Test

Run tests:

```bash
mvn test
```

Build the project:

```bash
mvn clean package
```

The project is packaged as a WAR artifact.

## Notes

- The application uses HTTP session state for authentication.
- `spring.jpa.hibernate.ddl-auto=update` is enabled, so the schema is updated automatically.
- Email delivery depends on valid SMTP credentials and provider configuration.
- The current forgot-password flow emails the existing stored password to the user.
