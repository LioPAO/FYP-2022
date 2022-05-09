FROM openjdk:17
COPY target/fyp.jar fyp.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "fyp.jar"]