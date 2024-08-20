# Stage 1: Build the application
FROM maven:3.9-amazoncorretto-19 as builder

# Copy the project files to the container
COPY ./pom.xml ./pom.xml

# Download all required dependencies into one layer
RUN mvn dependency:go-offline -B

# Copy your other files
COPY ./src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Create the runtime image
FROM amazoncorretto:19-alpine

# Copy the built artifact from the builder stage
COPY --from=builder /target/*.jar /app/spring-app.jar

EXPOSE 8443

# Run the application
# ENTRYPOINT ["java", "-jar", "-XX:+PrintFlagsFinal", "/app/spring-app.jar"]
# Run the application
ENTRYPOINT ["java", "-jar", "-XX:+PrintFlagsFinal", "-XX:+UseG1GC", "-XX:MinRAMPercentage=60", "-XX:MaxRAMPercentage=60", "/app/spring-app.jar"]
