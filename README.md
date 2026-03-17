# Mendel Transactions Challenge

REST API for storing transactions in memory.

## Tech stack

Java 17
Spring Boot 3.5.11
Docker
JUnit
OpenAPI

## Run locally

mvn clean package

java -jar target/transactions-1.0.0.jar

## Docker

docker build -t transactions .
docker run -p 8080:8080 transactions

## Swagger

http://localhost:8080/swagger-ui.html
