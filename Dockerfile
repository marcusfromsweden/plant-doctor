# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

# Command to execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]