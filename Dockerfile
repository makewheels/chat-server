#FROM maven:3.6.3-jdk-8 AS builder
##RUN git clone https://github.com/makewheels/chat-server
#ADD ./pom.xml pom.xml
#ADD ./src src/
##RUN cd chat-server
#RUN mvn clean package
#
#FROM openjdk:8-jre-alpine
#COPY --from=builder "target/chat-server-0.0.1-SNAPSHOT.jar" "app.jar"
#EXPOSE 5007
#CMD ["java","-jar","-Dspring.profiles.active=product","app.jar"]

FROM maven:3.6.3-jdk-8
RUN mvn clean package
RUN cp "target/chat-server-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 5007
CMD ["java","-jar","-Dspring.profiles.active=product","app.jar"]