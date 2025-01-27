FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/src/main/resources/ /app/resources/
EXPOSE 8081

ENV SPRING_SQL_INIT_SCHEMA_LOCATIONS=file:/app/resources/schema.sql
ENV SPRING_SQL_INIT_DATA_LOCATIONS=file:/app/resources/data.sql

ENTRYPOINT ["java", "-jar", "app.jar"]