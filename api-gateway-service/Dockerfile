FROM openjdk:17-ea-slim-buster
VOLUME /tmp
COPY build/libs/api-gateway-service-0.0.1-SNAPSHOT.jar api-gateway-service.jar
ENTRYPOINT ["java", "-jar", "api-gateway-service.jar"]