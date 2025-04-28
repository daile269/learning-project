# Stage 1: Build with Maven
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="daile"

# Set working directory in the container
WORKDIR /app

# Copy only the user-service project into /app
COPY ./user-service /app

# Run Maven to build the application (from /app, where pom.xml is located)
RUN mvn clean package -DskipTests

# Stage 2: Run with OpenJDK
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
