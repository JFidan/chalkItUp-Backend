# --------- STAGE 1: Build mit Maven (JDK 21) ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Für Dependency-Caching
COPY ../../pom.xml .
COPY ../../src ./src
COPY ../../.mvn .mvn
COPY ../../mvnw .
RUN ./mvnw dependency:go-offline


RUN ./mvnw clean package -DskipTests

# --------- STAGE 2: Runtime mit kleinem JDK 21-Image ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Kopiere das gebaute JAR von Stage 1
COPY --from=builder /app/target/*.jar app.jar

# Port für Doku
EXPOSE 8080

# Startbefehl
ENTRYPOINT ["java", "-jar", "app.jar"]
