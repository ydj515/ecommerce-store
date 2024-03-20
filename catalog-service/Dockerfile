FROM openjdk:17-ea-slim-buster
VOLUME /tmp
COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar catalog-service.jar
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]