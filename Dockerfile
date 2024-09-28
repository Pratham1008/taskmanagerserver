FROM alpine/java:22-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/TaskManagerBackend-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]