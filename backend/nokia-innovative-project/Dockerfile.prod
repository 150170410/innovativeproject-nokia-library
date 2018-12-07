FROM openjdk:8-jre-alpine
COPY ./target/nokia-innovative-project-0.0.1-SNAPSHOT.jar /usr/source/be.jar
WORKDIR /usr/source/
CMD java -Dserver.port=$PORT -Xms256m -Xmx256m -Xss512k -jar be.jar
