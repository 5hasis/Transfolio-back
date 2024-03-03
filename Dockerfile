FROM adoptopenjdk/openjdk11

ARG JAR_PATH=build/libs/Transfolio-0.1.jar

ADD ${JAR_PATH} trans-dev.jar

ENTRYPOINT [ "java", "-jar", "trans-dev.jar" ]