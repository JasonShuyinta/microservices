FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /cloud-gateway.jar
ENTRYPOINT ["java","-jar","/cloud-gateway.jar"]