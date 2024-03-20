FROM openjdk:17-ea-slim-buster
VOLUME /tmp
COPY build/libs/order-service-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java", "-jar", "order-service.jar"]