# Stage 1: Build with Maven
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="daile"

# Set working directory inside the container
WORKDIR /app

# Copy only the auth-service source code (which includes pom.xml)
COPY ./auth-service /app

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Stage 2: Run with OpenJDK
FROM openjdk:17-jdk-slim

# Set working directory for running the app
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
