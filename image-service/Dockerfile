FROM eclipse-temurin:17-jre-jammy

RUN curl -o /usr/bin/mc https://dl.min.io/client/mc/release/linux-amd64/mc && \
    chmod +x /usr/bin/mc

WORKDIR /opt/app
EXPOSE 8083
COPY target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]