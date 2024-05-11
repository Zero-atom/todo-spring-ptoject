FROM eclipse-temurin:21
EXPOSE 8080
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]