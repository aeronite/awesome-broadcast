FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./target/awesome-broadcaster-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/awesome.jar
WORKDIR /app

CMD ["java", "-server", "-jar", "awesome.jar"]