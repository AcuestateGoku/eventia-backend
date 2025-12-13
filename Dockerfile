#  Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Generamos el .jar
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Run)
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiamos el JAR generado
COPY --from=build /app/target/eventia-backend-0.0.1-SNAPSHOT.jar app.jar

# Copiamos la carpeta de Wallet específica
# Origen: src/main/resources/EventiaDB (Carpeta Real)
# Destino: /app/EventiaDB (Dentro del contenedor)
RUN mkdir -p /app/EventiaDB
COPY src/main/resources/EventiaDB /app/EventiaDB

# Configuramos la variable TNS_ADMIN apuntando a esa carpeta
ENV TNS_ADMIN=/app/EventiaDB

# Puerto
EXPOSE 8080

# Arrancar
ENTRYPOINT ["java", "-jar", "app.jar"]