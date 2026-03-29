# Estágio 1: Build (Compilação)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Execução (Rodar a aplicação)
FROM eclipse-temurin:17-jre
WORKDIR /app
# O nome abaixo deve ser exatamente o que está no <finalName> do pom.xml + .jar
COPY --from=build /app/target/gadys-backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]