FROM maven:3.6.3-openjdk-11 AS builder
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn dependency:go-offline
RUN mvn package -DskipTests

FROM openjdk:11-jdk-oracle
COPY --from=builder /usr/src/app/target/*.jar junit-app.jar
RUN java -jar junit-app.jar


