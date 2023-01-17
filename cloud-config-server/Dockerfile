FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /cloud-config.jar
ENTRYPOINT ["java","-jar","/cloud-config.jar"]