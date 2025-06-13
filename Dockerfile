FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=build/libs/Transfolio-0.1.jar

ADD ${JAR_FILE} Transfolio-0.1.jar

ENTRYPOINT [ "java", "-jar", "Transfolio-0.1.jar" ]