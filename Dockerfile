FROM maven:3.6.3-jdk-8
#RUN git clone https://github.com/makewheels/chat-server
RUN cd /workspace
RUN mvn clean package
ADD /workspace/target/chat-server-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 5007
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=product","/app.jar"]