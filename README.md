# URL Shortener API

A simple Kotlin + Spring Boot application that shortens URLs and supports redirection.

## Running Locally

Make sure you have PostgreSQL running and update .env.properties with your DB credentials.

Then run the project using Gradle:

./gradlew build
./gradlew bootRun

The application will start on http://localhost:8080.

## Example API Requests

You can find example API requests in:

scratches/generated-requests.http

It includes sample POST and GET requests for shortening and resolving URLs.

## Health Checks

Health and info endpoints are exposed via Spring Actuator:

GET http://localhost:8080/actuator/health

GET http://localhost:8080/actuator/info

