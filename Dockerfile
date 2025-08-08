# Use Java 17 instead of 21
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle dependencies --no-daemon || true

RUN gradle clean bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
