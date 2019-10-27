# Covering Note

## Technology Choices
* Kotlin - I haven't had much experience with Kotlin but really want to learn it, I thought this is a good opportunity to learn some new skills and improve my kotlin
* Maven - I used maven as my build runner tool as I have the most experience with it and started off with the Kotlin Maven Archetype.
* JUnit5 - I used JUnit4 for my example unit tests. I also used parameterised unit tests using junit-jupiter-params.
* Gson - For Json serialisation of the objects because I have used it previously and familiar with it.
* Git-flow - Version control branching strategy, I find it useful releasing working parts of the application in increments and git flow is great to do this (I won't be following it totally as I will not delete the feature branches so you can see my process)
 
## How to run
This project has been built using the git flow branching strategy each task will be in a separate feature then "released" to master.
Depending on how far I get there should be a pattern ```feature/task<number>```. If you check out to that branch and run below it should work and you can see my process of thinking.
### To start
```
chmod +x buld_app.sh
./build_app.sh
```
### To stop
```docker-compose down```
### Final solution
To view the logging please use as the docker compose currently runs detached as the output with all services is not very readable for humans.
```docker-compose logs <name of your service that was defined in docker-compose file>```

### Basic Solution
The basic solution was to deserialize the stream into json. To make sure I don't fill your space up the app will only take the first 100 objects and save them to a json file.
This should also be small enough to run through a simple json validator.
The output file will be stored  in ```output/output.json``` it should be blank before you run the application. It is mounted to the docker container so no need to go into the container to find the result.
```
git checkout feature/taskOne
chmod +x buld_app.sh
./build_app.sh
```

### Dependencies
* Docker
* Docker Compose
* Maven
* Java 8 +


