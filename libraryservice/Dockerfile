FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/libraryservice-3.4.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL maintainer="msh479859@gmail.com"
LABEL name="libraryservice"