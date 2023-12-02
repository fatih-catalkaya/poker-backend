FROM gradle:8.2.0-jdk17-alpine AS builder
WORKDIR /app
COPY src ./src
COPY build.gradle ./
RUN ["gradle", "shadowJar"]

FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/backend.jar /app/backend.jar
CMD ["java", "-jar", "backend.jar"]