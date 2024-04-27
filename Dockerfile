FROM openjdk:21
WORKDIR /home/trivia-question-api
COPY target/trivia-questions-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "--enable-preview", "-jar", "trivia-questions-0.0.1-SNAPSHOT.jar"]