FROM jelastic/maven:3.9.4-openjdk-22.ea-b17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim
COPY --from=build /target/TaskManagerBackend-0.0.1.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar","app.jar"]