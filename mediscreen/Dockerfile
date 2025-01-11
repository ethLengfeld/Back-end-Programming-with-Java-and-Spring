# Use a base image with Java installed
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/mediscreen-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the port your application listens on
EXPOSE 8081

# Command to run when the container starts
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
