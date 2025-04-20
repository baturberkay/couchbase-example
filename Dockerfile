FROM openjdk:21-jdk-slim AS build
LABEL authors="batur"

WORKDIR /app
COPY . .
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /couchbase-example

# Wildcard used correctly
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
