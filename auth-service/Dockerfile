FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8082
COPY target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]