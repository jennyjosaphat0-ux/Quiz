# Etap 1: Konstwi pwojè a ak Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etap 2: Lanse aplikasyon an ak Java
FROM openjdk:17-jdk-slim
WORKDIR /app
# Kopi fichye .jar ki fenk fèt la
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Lanse aplikasyon an
ENTRYPOINT ["java", "-jar", "app.jar"]