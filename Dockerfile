FROM openjdk:11-jdk
RUN apt-get update && apt-get -y install sudo
ARG JAR_FILE="./build/libs/*.jar"
COPY ${JAR_FILE} task.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar","/task.jar"]