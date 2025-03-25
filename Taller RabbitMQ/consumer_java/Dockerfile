FROM openjdk:11
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y wget
RUN wget https://repo1.maven.org/maven2/com/rabbitmq/amqp-client/5.13.0/amqp-client-5.13.0.jar
RUN wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar
RUN wget https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.30/slf4j-simple-1.7.30.jar
RUN javac -cp "amqp-client-5.13.0.jar:slf4j-api-1.7.30.jar:slf4j-simple-1.7.30.jar" Consumer.java
CMD ["java", "-cp", ".:amqp-client-5.13.0.jar:slf4j-api-1.7.30.jar:slf4j-simple-1.7.30.jar", "Consumer"]
