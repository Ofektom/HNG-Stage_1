FROM openjdk:21
VOLUME /tmp
COPY /target/*.jar /app.jar
WORKDIR /app
EXPOSE 8085
CMD ["java", "-jar", "/app.jar"]