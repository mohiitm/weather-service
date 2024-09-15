FROM openjdk:17-jdk
VOLUME /tmp
COPY target/weather-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
