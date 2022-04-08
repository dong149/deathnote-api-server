FROM openjdk:11.0.12
ARG JAR_FILE=./build/libs/deathnote-api-server-0.0.2-SNAPSHOT.jar
VOLUME /tmp
ENV PROFILE=production
ENV JAVA_OPTS=""
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=production","/app.jar"]