FROM openjdk:11
COPY ./target/matchmaking-0.0.1-SNAPSHOT.jar /matchmaking.jar
EXPOSE 8300
ENTRYPOINT ["java", "-jar", "matchmaking.jar"]