FROM openjdk:8
COPY feedme-producer/target/feedme-producer-1.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar", "provider", "8282", "kafka", "9092"]
