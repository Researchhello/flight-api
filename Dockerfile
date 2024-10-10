FROM openjdk:21-slim
EXPOSE 8090

ADD target/flight-api.jar flight-api.jar

ENTRYPOINT ["java", "-jar", "/flight-api.jar"]