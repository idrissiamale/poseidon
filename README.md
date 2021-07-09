# Poseidon
Web application which uses Java, Spring Boot framework and MySQL as database. 

This app will be used to help generate more transactions for institutional investors who purchase or sell fixed-income securities.


## Getting Started
### Prerequisites

What things you need to install the Poseidon software and how to install them :

- Java 1.8
- Maven 3.8.1
- Spring Boot 2.0.4 RELEASE
- Thymeleaf
- Bootstrap 4.3.1
- MySQL 8.0.22

### Installing

A step by step series of examples that tell you how to get a development environment running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Spring Boot:

https://spring.io/guides/gs/spring-boot/ : a step by step tutorial.

https://start.spring.io : please select the following dependencies before installing Spring Boot:
- Spring Web
- Spring data JPA
- Spring Security 
- Thymeleaf 

4.Install MySQL:

https://dev.mysql.com/downloads/mysql/

### Running App

Import the code into an IDE of your choice and run the SpringbootApplication.java to launch the application.

### Testing

The app has unit tests written which need to be triggered from maven-surefire plugin. Please use JaCoCo for the code coverage.

1. To run the unit tests from maven, execute the command below.

`mvn test`

2. To run code coverage with JaCoCo from maven, execute the following command

`mvn verify`

3. To generate the Surefire Report, please use the command below

`mvn site`