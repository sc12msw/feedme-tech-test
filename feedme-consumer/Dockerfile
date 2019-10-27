FROM openjdk:8
COPY feedme-consumer/target/feedme-consumer-1.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar", "kafka", "9092"]