FROM maven:3.8.5-openjdk-11-slim as build
WORKDIR /app
COPY pom.xml .
COPY ./TimeUpBackend/src/ ./src/
RUN mvn -Dmaven.test.skip package

FROM openjdk:11
EXPOSE 8080
ARG JARFile=/app/target/TimeUpBackend.jar
COPY  --from=build ${JARFile} app.jar
ENTRYPOINT ["java","-jar","app.jar"]