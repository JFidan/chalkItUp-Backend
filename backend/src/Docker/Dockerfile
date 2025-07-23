# --------- STAGE 1: Build mit Maven ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Kopiere pom.xml und lade dependencies (für Caching)
COPY ../../pom.xml .
RUN mvn dependency:go-offline

# Jetzt den Rest der App kopieren und bauen
COPY ../../src ./src
RUN mvn clean package -DskipTests

# --------- STAGE 2: Runtime ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Kopiere das gebaute JAR aus Stage 1
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
