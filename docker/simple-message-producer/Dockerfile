FROM openjdk:8

COPY apache-pulsar-examples-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/apache-pulsar-examples-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY startup.sh /opt/startup.sh

WORKDIR /opt

CMD ["./startup.sh"]