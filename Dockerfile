FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x ./mvnw

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/app.jar"]
