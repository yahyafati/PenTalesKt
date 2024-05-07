FROM openjdk:17-jdk
WORKDIR /app

# list the files in the builder

COPY build/libs/PenTalesREST-0.0.1-SNAPSHOT.jar app.jar
#COPY src/main/resources/*.yml .

ARG SPRINGBOOT_DOCKER_PORT
ENV SPRINGBOOT_DOCKER_PORT=$SPRINGBOOT_DOCKER_PORT
EXPOSE $SPRINGBOOT_DOCKER_PORT

ENTRYPOINT ["java", "-jar", "app.jar"]
