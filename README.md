# user-api

## Requirements

For building and running the application you need:

- [JDK 11]([http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](https://www.oracle.com/java/technologies/downloads/#java11))
- [Maven 3](https://maven.apache.org)




### Running the code locally

There are several ways to run a Spring Boot application on local machine. One way is to execute the `main` method in the `com.jitpay.users.userapi.UserApiApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## To run alls the tests

```shell
mvn clean test
```

## Api doc will be available in 
```shell
http://localhost:8081/swagger-ui/index.html
```
