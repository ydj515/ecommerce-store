FROM openjdk:17-ea-slim-buster
VOLUME /tmp
COPY build/libs/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]