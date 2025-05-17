FROM maven:3.8.7-eclipse-temurin-17 AS builder

RUN curl -o /usr/bin/mc https://dl.min.io/client/mc/release/linux-amd64/mc && \
    chmod +x /usr/bin/mc

WORKDIR /opt/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8083
COPY --from=builder /opt/app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]