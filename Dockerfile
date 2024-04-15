FROM openjdk:21-jdk

WORKDIR /home/gradle/project

COPY . .
RUN mkdir -p /root/.gradle && echo -e "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties
RUN chmod +x ./gradlew && ./gradlew clean build
CMD ["java", "-jar", "/home/gradle/project/build/libs/codeython-0.0.1-SNAPSHOT.jar"]
