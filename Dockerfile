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

# Check what's in the target directory
RUN ls -la target/

# Run the application - using the correct JAR name based on finalName in pom.xml
CMD ["java", "-jar", "target/user-ws.jar"]
