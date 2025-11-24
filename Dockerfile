# ===========================
# Fase 1: Construcción
# ===========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto
RUN mvn clean package -DskipTests

# ===========================
# Fase 2: Imagen final
# ===========================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/target/*.jar app.jar

# Instalar wait-for-it para dependencias
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Exponer el puerto
EXPOSE 9090

# Comando de inicio (usando variables de entorno)
ENTRYPOINT ["java", "-jar", "app.jar"]