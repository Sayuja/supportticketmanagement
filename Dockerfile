# Runtime image only - JAR is pre-built by CI
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy pre-built JAR from CI
COPY target/*.jar app.jar

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
