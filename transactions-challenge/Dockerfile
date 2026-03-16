FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/transactions-api-1.0.0.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]