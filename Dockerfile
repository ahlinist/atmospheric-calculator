FROM openjdk:11
COPY ./build/libs/*.jar /usr/app/
WORKDIR /usr/app
CMD exec java -Xms128m -Xmx512m -jar calculator*SNAPSHOT.jar
