FROM openjdk:21-slim
FROM node
WORKDIR /home/gradle/project
COPY . .

#ENV JAVA_HOME /usr/lib/jdk
#ENV PATH=${PATH}:${JAVA_HOME}/bin

RUN mkdir -p /root/.gradle && \
    echo -e "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties
RUN chmod +x ./gradlew && ./gradlew clean build

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/codeython-0.0.1-SNAPSHOT.jar app.jar
#COPY src/main/resources/application-prod.yaml resources/application-prod.yaml
CMD ["java", "-jar", "app.jar"]
