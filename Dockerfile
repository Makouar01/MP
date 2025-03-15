# Utiliser une image légère de JDK 17
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR dans le conteneur
COPY target/MP-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port utilisé par Spring Boot
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
