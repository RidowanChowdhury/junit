FROM maven:3.6.3-openjdk-11 AS builder
WORKDIR /usr/src/app
COPY . /usr/src/app
# To run the tests,
# RUN mvn test

# To run the application,
RUN mvn package -DskipTests

FROM openjdk:11-jdk-oracle
COPY --from=builder /usr/src/app/target/*.jar junit-app.jar
ENTRYPOINT ["java", "-jar", "/junit-app.jar"]


