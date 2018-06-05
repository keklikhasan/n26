# N26 Interview Application

This project is for coding challenge of N26 job application. This apps provides a restful API that save transaction and statistic of transactions. This project is a maven based Spring Boot application.  


## Configure and Run App

* App's configuration files located under /src/main/resources we have application.yaml, application-local.yaml (your local env if you want to change).

## Tests
* You can start test `mvn test`

## Run Application
* To run you can run this maven command `mvn clean install` and `mvn spring-boot:run` 
* You can run curl 
  * `curl --request POST --url http://localhost:8080/transactions --header 'content-type: application/json' --data '{"amount": 12.3,"timestamp": 1528247562034}'` 
  * `curl --request GET --url http://localhost:8080/statistics --header 'content-type: application/json'`
* Or I gave export project for rest call [insomnia]('https://insomnia.rest/') under folder insomnia.


## Useful Tools

* I use [insomnia]('https://insomnia.rest/') for rest client i exported project to in project.
* I use swagger to documentation you can reach [api-dcos(http://localhost:8080/v2/api-docs)]('http://localhost:8080/v2/api-docs')
 and [swagger-ui(http://localhost:8080/swagger-ui.html)]('http://localhost:8080/swagger-ui.html')