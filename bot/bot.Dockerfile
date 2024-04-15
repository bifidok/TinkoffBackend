FROM openjdk:21
ARG JAR_FILE=bot/target/bot.jar
WORKDIR /opt/app
COPY ${JAR_FILE} bot.jar
EXPOSE 8090
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","bot.jar"]
