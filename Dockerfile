FROM openjdk:11-jdk-oracle
COPY target/*.jar junit-app.jar
ENTRYPOINT ["java", "-jar", "/junit-app.jar"]