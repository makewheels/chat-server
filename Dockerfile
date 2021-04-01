FROM maven:3.6.3-jdk-8
#RUN git clone https://github.com/makewheels/chat-server
ADD ./pom.xml pom.xml
ADD ./src src/
#RUN cd chat-server
RUN mvn clean package
ADD "target/chat-server-0.0.1-SNAPSHOT.jar" "/app.jar"
EXPOSE 5007
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=product","/app.jar"]