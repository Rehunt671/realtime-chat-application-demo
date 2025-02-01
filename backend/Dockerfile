# Build Stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and resolve dependencies separately to leverage caching
COPY pom.xml /app/
RUN mvn dependency:resolve

# Copy source code and build the application
COPY src /app/src/
RUN mvn clean package -Dmaven.test.skip=true

# Production Stage
FROM eclipse-temurin:21-jre AS production

LABEL maintainer="nonokub.671@gmail.com"

WORKDIR /app

# Copy the compiled JAR file (assuming only one JAR is generated)
COPY --from=build /app/target/*.jar /app/chat-app.jar

# Copy configuration file
COPY src/main/resources/application.properties /app/config/application.properties

# Set environment variable for Spring Boot config location
ENV SPRING_CONFIG_LOCATION=/app/config/application.properties

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/chat-app.jar"]
