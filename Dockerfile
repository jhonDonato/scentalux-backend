# Fase de construcción
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar pom.xml primero (para cache de dependencias)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Fase de ejecución
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar el JAR
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto
EXPOSE 9090

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]