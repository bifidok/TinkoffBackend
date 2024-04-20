FROM openjdk:21
ARG JAR_FILE=./scrapper/target/scrapper.jar
WORKDIR /opt/app
COPY ${JAR_FILE} scrapper.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","scrapper.jar"]
