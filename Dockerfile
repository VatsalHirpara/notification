FROM openjdk:11.0-jre

WORKDIR /tmp

COPY target/notification-0.0.1-SNAPSHOT.jar /tmp/notification-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/tmp/notification-0.0.1-SNAPSHOT.jar"]

EXPOSE 9191