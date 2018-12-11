FROM openjdk:11-jre-slim

ADD target/transitive-0.0.1-SNAPSHOT.jar /transitive.jar
ADD docker/entrypoint.sh /entrypoint.sh

RUN chmod a+x /entrypoint.sh

ENV JAVA_OPTS=""

ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 8080