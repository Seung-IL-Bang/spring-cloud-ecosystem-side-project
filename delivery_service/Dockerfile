FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]