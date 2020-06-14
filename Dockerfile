FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/spring-leverx.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]