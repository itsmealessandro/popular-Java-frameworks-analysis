# Thesis Project: Assessing Response Times and Resources Utilization of Java Web Frameworks

This repository contains the source code and configurations used in the thesis project for the **Computer Science** degree at the **University of L'Aquila**. The aim of the thesis is to assess the response times and resource utilization of different Java web frameworks, using the **Spring PetClinic REST API** as a case study.

## Thesis Title
**Assessing Response Times and Resources Utilization of Java Web Frameworks**

## Author
Alessandro Di Giacomo

## University
**University of L'Aquila**  
Department of Computer Science (DISIM)

## Supervisor
Daniele Di Pompeo

---

## Project Overview

### Objective
The main goal of this thesis is to analyze the performance of Java web frameworks by assessing their **response times** and **resource utilization** under varying loads. The **Spring PetClinic REST API** serves as the sample application, and **Locust** is used for generating HTTP traffic and analyzing the performance of the API.

### Tools and Technologies
- **Frameworks**:
  - *Spring*
  - *Quarkus*
- *spring-petclinic GitHub repository*: [spring-petclinic-rest](https://github.com/spring-petclinic/spring-petclinic-rest)
- **Locust**: A Python-based load testing tool for measuring performance under different scenarios.
  - Official website: [https://locust.io/](https://locust.io/)
- **Docker**: Used to containerize the application and set up isolated testing environments.
- **Java**: A long-term support (LTS) version of the Java platform, utilized to ensure compatibility and performance.
- **JAX-RS**: The Java API for RESTful Web Services
- **Panache**: Quarkus hibernate ORM
- **Flyway**: Simplify DB migrations
  
## Project Structure

```bash
.
├── analysis_spring.sh
├── LICENSE
├── locust
│   ├── allTests.py
│   ├── crudTest.py
│   ├── __pycache__
│   └── reports
├── micronautguide
│   ├── aot-jar.properties
│   ├── aot-native-image.properties
│   ├── micronaut-cli.yml
│   ├── mvnw
│   ├── mvnw.bat
│   ├── pom.xml
│   ├── README.md
│   ├── src
│   └── target
├── quarkus-petclinic
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── README.md
│   ├── src
│   └── target
├── README.md
├── spring-petclinic-rest-master
│   ├── docker-compose.yml
│   ├── LICENSE.txt
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── petclinic-ermodel.png
│   ├── pom.xml
│   ├── readme.md
│   ├── src
│   └── target
└── test.sh
```

## Running

### Run quarkus pet-clinic
The following commands will run the quarkus pet-clinic REST API
```
cd quarkus-petclinic/
./mvnw clean compile package quarkus:run -Ddb=1
```

### Run spring pet-clinic
The following commands will run the spring pet-clinic REST API
```
cd spring-petclinic-rest-master/
./mvnw spring-boot:run -Dspring-boot.run.arguments="--db=1" 
```

