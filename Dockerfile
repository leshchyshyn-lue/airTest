FROM openjdk:18.0.1.1
WORKDIR /app
COPY target/air-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
