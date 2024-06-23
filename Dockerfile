FROM openjdk:18-jdk-alpine

RUN apk update && apk add --no-cache maven

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

# Build the project and package the application
RUN mvn package -DskipTests

# Copy the JAR file to the correct location
COPY target/*.jar app.jar

EXPOSE 25565

CMD ["java", "-jar", "app.jar"]
