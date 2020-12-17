FROM maven:3.6.3-openjdk-11
EXPOSE 8080
ENV APP_DIR "/usr/app"
COPY pom.xml ${APP_DIR}/
COPY src ${APP_DIR}/src
WORKDIR ${APP_DIR}
ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=docker"]