# First stage: Build stage
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY src /app/src
RUN gradle build

# Second stage: Minimal runtime environment
FROM openjdk:17-alpine
WORKDIR /app
# Copy jar from the first stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
