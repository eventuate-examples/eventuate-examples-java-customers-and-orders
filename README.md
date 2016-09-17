This is the Java version of the customers and orders example that I've used in numerous presentations
on developing microservices with event sourcing and CQRS.
The code is built using the Eventuate platform.
It illustrates how to implement an eventually consistent credit limit check using event sourcing.
For more information, see this [presentation from Gluecon 2016](http://www.slideshare.net/chris.e.richardson/a-pattern-language-for-microservices-gluecon-2016/24)

# About Eventuate&trade;

![](http://eventuate.io/i/logo.gif)

[Eventuate](http://eventuate.io/) is a application platform for writing transaction microservices.
It provides a simple yet powerful event-driven programming model that is based on event sourcing and Command Query Responsibility Segregation (CQRS).
Eventuate solves the distributed data management problems inherent in a microservice architecture.
It consists of a scalable, distributed event store server and client libraries for various languages and frameworks including Java, Scala, and the Spring framework. [Learn more.](http://eventuate.io/)

# Signing up for Eventuate

To run the application you need credentials for the Eventuate platform.
You can get them by [signing up here](https://signup.eventuate.io/).

# Building the application

This application is written using Java 8.
You can then build the application using this Gradle command:

```
./gradlew assemble
```

Note: to use Gradle you just need to have JDK 8 in your path. You do not need to install Gradle.

# Running

Now that you built the application you can run it using this command:

```
docker-compose up -d
```

# Using the application

Once the application is running you can visit the following URLs to create customers, create orders, and view the order history:

* `http://${DOCKER_HOST_IP?}:8081/swagger-ui.html` - Create a customer
* `http://${DOCKER_HOST_IP?}:8083/swagger-ui.html` - Create an order
* `http://${DOCKER_HOST_IP?}:8082/swagger-ui.html` - View the customer and the order

(Hint: best to open these URLs in separate tabs)

Note: DOCKER_HOST_IP is the IP address of the machine running the Docker daemon.
