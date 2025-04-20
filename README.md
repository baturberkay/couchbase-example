# CouchbaseDB Example

This is a Spring Boot project that includes basic configurations for a REST API, CouchbaseDB session management, and Swagger for API documentation.

## Prerequisites

- **Java 21**
- **Docker & Docker Compose**

## Dependencies

- Spring Boot Web
- Spring Session
- Couchbase Java SDK
- Swagger / Springdoc OpenAPI

## How to Run the Application

You can run the application locally, but using Docker simplifies the setup and is the preferred approach in this example.

### 1. Build the Docker Images

```bash
docker-compose build
```

### 2. Start and Configure Couchbase

Start the Couchbase service:

```bash
docker-compose up couchbase
```

Then, in your browser, go to [http://localhost:8091](http://localhost:8091) and follow these steps to initialize Couchbase:

- Complete the cluster setup wizard.
- Use the following configuration:
    - **Username**: `Administrator`
    - **Password**: `Administrator`
- After setup is complete:
    - From the **left menu**, click on **"Buckets"**
    - On the Buckets page, click the **"Add Bucket"** button in the **top-right corner**
    - Enter `session_bucket` as the bucket name and complete the creation process

> 📝 Make sure the bucket name exactly matches the one configured in your application.

### 3. Start the Spring Boot App

Once Couchbase is fully set up and the bucket is created:

```bash
docker-compose up app
```

## Swagger

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## API Endpoints

The application exposes a sample endpoint:

- **GET** `/api/v1/session/create` — Creates and returns a new session.
