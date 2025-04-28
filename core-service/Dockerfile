# Stage 1: Build with Maven
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="daile"

# Set working directory in the container
WORKDIR /app

# Copy only the core-service source to the container
COPY ./core-service /app

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run with OpenJDK
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
