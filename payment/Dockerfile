FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /payment-service.jar
ENTRYPOINT ["java","-jar","/payment-service.jar"]