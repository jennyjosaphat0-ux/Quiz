# Etap 1: Konstwi pwojè a ak Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etap 2: Lanse aplikasyon an ak Java
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 10000
# Liy sa a enpòtan pou Render ka jwenn pò a
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]