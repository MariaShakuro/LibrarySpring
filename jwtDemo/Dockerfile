FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/jwtDemo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL maintainer="msh479859@gmail.com"
LABEL name="jwtdemo"