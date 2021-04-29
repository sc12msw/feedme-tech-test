# Covering Note

## Technology Choices
* Kotlin - I haven't had much experience with Kotlin but really want to learn it, I thought this is a good opportunity to learn some new skills and improve my kotlin
* Maven - I used maven as my build runner tool as I have the most experience with it and started off with the Kotlin Maven Archetype.
* JUnit4 - I used JUnit4 for my example unit tests. I also used parameterised unit tests using junit-jupiter-params.
* Gson - For Json serialisation of the objects because I have used it previously and familiar with it.
* Git-flow - Version control branching strategy, I find it useful releasing working parts of the application in increments and git flow is great to do this (I won't be following it totally as I will not delete the feature branches so you can see my process)
* Kafka Queues - Never used them before wanted to learn how it works. Was a lot of fun setting all these components up with docker-compose!
* Mongo DB - Have a little experience with Dynamo DB but I still think my skills lack here with NOSQL database. However was a great experience to try it out even though I only managed to get the writes working!
* Google Guice (DI) - Used Google Guice previously when developing dropwizard microservices. Only DI framework I have used before

## What I am delivering

1. Basic task:
    * Create an app that connects the provider service on the exposed TCP port  (Completed) 
    * Transform the proprietary data format into JSON using the field names and data types defined in the provider /types endpoint(Completed)
    * Write unit tests (Partial completion, I have written more of examples of tests that I can write, than fully testing the application this should give you insight into my
        testing skill. But I have by no means fully tested the application) 
2. Intermediate task:
    * Save the JSON into a NoSQL store with a document per fixture. (Partial completion, I managed to set up a mongo DB along side the application and write empty events to it without markets or outcomes. 
       I think I over complicated my solution in the first task which made this part more difficult. If I were to reattempt this task I would spend more time on my deserialization.)
    * Each document should contain the event data and the child markets and outcomes for the fixture.(Failed to complete, due to reason discussed in the previous point) 
3. Advanced task: 
    * Implement a way of sharding / partitioning the transformed JSON packets via one or more message queues (Partial completion, I set up at utilised Kafka to produce the json objects on the client and consume them on the consumer to write to the db)
    * Utilising the message queue(s) move your NoSQL logic into another app that can be run multiple times to enable concurrent NoSQL writes. (Partially completion, I managed to create two apps using maven that both produce and consume off the queue. However I did not implement the logic to have current writes)
4. Additional tasks:
    * Implement a front end that displays the hierarchical NoSQL data. Use the Sky Bet website for layout and navigational inspiration (Not attempted)
    * Create a Dockerfile for your app(s) (Complete)
    
    
## How to run
Although the service has not been finished it should run with all the components just writing the new events to the db using the following instructions:
### Final solution
To view the logging please use as the docker compose currently runs detached as the output with all services is not very readable for humans.
```docker-compose logs <name of your service that was defined in docker-compose file>```
### To start
```
chmod +x build_app.sh
./build_app.sh
```
### To stop
```docker-compose down```


### Basic Solution
The basic solution was to deserialize the stream into json. To make sure I don't fill your space up the app will only take the first 100 objects and save them to a json file.
This should also be small enough to run through a simple json validator.
The output file will be stored  in ```output/output.json``` it should be blank before you run the application. It is mounted to the docker container so no need to go into the container to find the result.
```
git checkout feature/taskOne
chmod +x build_app.sh
./build_app.sh
```

### Dependencies
* Docker
* Docker Compose
* Maven
* Java 8 +
