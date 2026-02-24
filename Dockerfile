FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY out/artifacts/fliply1/fliply.jar app.jar
ENTRYPOINT ["java", "-jar", "fliply.jar"]
