# Stage 1: Compilazione (AS build)
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copia il pom.xml e i sorgenti
COPY pom.xml .
COPY src ./src

# Compila il progetto saltando i test per velocizzare
RUN mvn clean package -DskipTests

# Stage 2: Esecuzione minimale
FROM eclipse-temurin-17-jre-alpine
WORKDIR /app

# Copia solo il file .jar generato dallo stage precedente
COPY --from=build /app/target/rubrica-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta dell'applicazione
EXPOSE 8080

# Comando di avvio
ENTRYPOINT ["java", "-jar", "app.jar"]