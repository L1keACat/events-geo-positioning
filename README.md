#### Build the project

In order to build the project edit _**application.properties**_ file to use your database.

Create (if you don't have) database with 1 collection: _**sequences**_.

Then insert 1 document(_**\_id**_: "events", _**sequence**_: 0)  in _**sequences**_.

In order to build the project itself run:

```
./gradlew build
```

#### Run the project:

```
./gradlew build
```
```
./gradlew bootRun
```

Or you can simply run:

```
java -jar ./build/libs/events-geo-positioning-0.0.1-SNAPSHOT.jar
```
Access http://localhost:8080

#### Enter as admin:

Username: _**admin**_.

Password: _**123**_.

#### Enter as user:

Just sign up with your own username and password and then log in.