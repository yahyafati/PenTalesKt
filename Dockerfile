# Use Oracle's official Java 17 image.
FROM openjdk:17

RUN microdnf install findutils

VOLUME [ "/root/.gradle" ]

# Set working directory in the container
WORKDIR /app

# Copy the Gradle build files and source code into the container
COPY . .

# Build the application
RUN chmod +x gradlew
RUN ./gradlew build -x test

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "build/libs/PenTalesREST-0.0.1-SNAPSHOT.jar"]
