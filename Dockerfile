FROM openjdk:8-jdk-alpine
ARG project_name=my-site
VOLUME /tmp
ADD ./target/my-site-1.0.2.RELEASE.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]