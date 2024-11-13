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
- **Spring PetClinic REST API**: A reference implementation of RESTful services using the Spring Framework.
  - GitHub repository: [spring-petclinic-rest](https://github.com/spring-petclinic/spring-petclinic-rest)
- **Locust**: A Python-based load testing tool for measuring performance under different scenarios.
  - Official website: [https://locust.io/](https://locust.io/)
- **Docker**: Used to containerize the application and set up isolated testing environments.
- **Java**: A long-term support (LTS) version of the Java platform, utilized to ensure compatibility and performance.
  
## Project Structure

```bash
.
├── learningArc
│   ├── code-with-quarkus       # Quarkus-related code and experiments
│   ├── demo                    # Miscellaneous demo code
│   ├── docker                  # Docker configurations and files
│   └── hello_world_micro        # Sample Hello World project with Micronaut
├── LICENSE                     # Project license information
├── micronautguide               # Micronaut-related code
│   └── target
├── README.md                   # Project documentation (this file)
├── rest-json-quickstart         # Quarkus REST API example
│   └── target
└── spring-petclinic-rest-master # Main PetClinic REST API code and testing scripts
    ├── docker-compose.yml       # Docker configuration for PetClinic and Locust
    ├── full_analysis_script.sh  # Script for running full analysis of the API
    ├── LICENSE.txt              # License for the Spring PetClinic project
    ├── locust                   # Locust load testing scripts
    ├── mvnw                     # Maven wrapper for building the project
    ├── mvnw.cmd
    ├── petclinic-ermodel.png    # ER diagram for the PetClinic database
    ├── pom.xml                  # Maven configuration for the project
    ├── readme.md                # README specific to Spring PetClinic REST API
    ├── src                      # Source code for the Spring PetClinic API
    ├── target                   # Build output directory
    └── test.sh                  # Script to execute tests on the PetClinic API

## Running

### Run quarkus pet-clinic
The following commands will run thr quarkus pet-clinic REST API
cd quarkus-petclinic/

./mvnw clean compile package quarkus:run -Ddb=1

### Run spring pet-clinic
cd spring-petclinic-rest-master/

./mvnw spring-boot:run -Dspring-boot.run.arguments="--db=1" 

