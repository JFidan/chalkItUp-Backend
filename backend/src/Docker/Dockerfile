FROM eclipse-temurin:17-jdk

# Arbeitsverzeichnis
WORKDIR /app

# Kopiere build/libs/*.jar (angenommen du nutzt Gradle oder Maven)
COPY build/libs/*.jar app.jar

# Service Account Key einbinden
COPY ../main/java/com/chalkItUp/chalkItUp/config/admin.json /app/config/admin.json

# Port (optional, z. B. 8080)
EXPOSE 8080

# ENV-Variablen (hier optional gesetzt – besser über Render später!)
ENV GOOGLE_CLOUD_PROJECT=chalkitup-95340
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/config/admin.json

# Startkommando
ENTRYPOINT ["java", "-jar", "app.jar"]
