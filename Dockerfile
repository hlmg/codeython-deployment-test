FROM gradle:8.5-jdk11 AS build
WORKDIR /home/gradle/project
COPY . .
RUN chmod +x ./gradlew && ./gradlew clean build

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar", "--spring.config.location=/home/gradle/project/application-prod.yaml"]
