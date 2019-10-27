FROM openjdk:8
COPY target/feedme-1.0-SNAPSHOT.jar /app/app.jar
COPY output/output.json /app/output/output.json
WORKDIR /app
CMD ["java", "-jar", "app.jar", "provider", "8282", "kafka", "9092"]
