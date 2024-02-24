FROM gradle:8.6.0-jdk17 as builder

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src/

RUN gradle build -x test --build-cache

FROM openjdk:17-slim
WORKDIR /app

# list the files in the builder

COPY --from=builder /app/build/libs/*.jar app.jar
COPY src/main/resources/*.yml .

ARG SPRINGBOOT_DOCKER_PORT
ENV SPRINGBOOT_DOCKER_PORT=$SPRINGBOOT_DOCKER_PORT
EXPOSE $SPRINGBOOT_DOCKER_PORT

ENTRYPOINT ["java", "-jar", "app.jar"]
