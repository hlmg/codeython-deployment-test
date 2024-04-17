FROM gradle:8.5-jdk AS build
WORKDIR /home/gradle/project
COPY . .
RUN mkdir -p /root/.gradle && echo -e "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties
RUN chmod +x ./gradlew && ./gradlew wrapper --gradle-version=8.5 && ./gradlew clean build

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar", "--spring.config.location=/home/gradle/project/application-prod.yaml"]
