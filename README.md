# Auth Microservice

## Summary
This is the **authentication microservice** for the LiftBro social networking app. It is built with **Java**, **Spring Boot**, and **MySQL**, and currently supports login via **Google OAuth 2.0** only.
Users are registered or found in the MySQL table, oauth_users. This service validates identity, then (and this is coming shortly) issues a signed JWT token that other services can trust.

---

## 🛠 Tech Stack

- Java 23
- Spring Boot 3.5
- Spring Security
- Spring Data JPA
- MySQL
- OAuth 2.0 (Google)

---

## 🚀 Features
- Login via Google OAuth 2.0
- Creates or updates an entry in the `oauth_users` table upon login
- Exposes a `/auth/me` endpoint to fetch the authenticated user
- Fully integrated with Spring Security
- Currently uses session-based authentication (JWT integration is planned)

## 🗃️ Database Schema

### `oauth_users` table

Stores minimal authentication data:

| Field       | Type       | Description                      |
|-------------|------------|----------------------------------|
| id          | Long (PK)  | Primary key                      |
| email       | String     | User's email                     |
| name        | String     | Full name from Google            |
| provider    | String     | e.g., `"google"`                 |
| providerId  | String     | Google `sub` claim (unique ID)   |
| createdAt   | Timestamp  | Time user was first created      |
| lastLogin   | Timestamp  | Time user last logged in         |

> A separate `users` table exists in a different microservice for user profile data.

### `users` table (partial)

Stores user data with oauth_users as a FK:

| Field         | Type      | Description                    |
|---------------|-----------|--------------------------------|
| id            | Long (PK) | Primary key                    |
| email         | String    | User's email                   |
| name          | String    | Full name from Google          |
| address       | String    | User's home address            |
| oauth_user_id | Long (FK) | ID from `oauth_users`          |
| createdAt     | Timestamp | Time user was first created    |
| lastLogin     | Timestamp | Time user last logged in       | 

---

## 📦 Running the App

### ✅ Prerequisites

- Java 17+
- Maven or Gradle
- MySQL (local or cloud-based)

### ⚙️ Configuration

Set the following properties in `application.yml` or `application.properties`:


spring:
  datasource:
    url: jdbc:mysql://localhost:3306/liftbro_auth
    username: your_mysql_user
    password: your_mysql_password
  jpa:
    hibernate:
      ddl-auto: update

  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_GOOGLE_CLIENT_ID
            client-secret: YOUR_GOOGLE_CLIENT_SECRET
            scope: openid, profile, email

### ▶️ Run locally

`./gradlew bootRun` or `./mvnw spring-boot:run`

Visit:
http://localhost:8080/auth/me

After login, the user will be redirected and stored in the oauth_users table.

### 🔍 API Endpoints
GET /auth/me
Returns the authenticated user's basic info:

Example Response

{
"id": 1,
"email": "zaphod@gmail.com",
"name": "Zaphod Beeblebrox",
"provider": "google",
"providerId": "105829624491431576614",
"createdAt": "2025-06-29T08:51:23Z",
"lastLogin": "2025-06-29T09:01:45Z"
}