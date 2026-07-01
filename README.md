Lclass-backend

This repository contains the assets that is needed for hosting the back end for the Lclass learning 
management system we have three microservices currently namely authentication microservices, api gateway and Lclass
service discover microservices.

Authentication Microservices
what it does is that it makes sure the username and password matches the in the database and gives out 
json token to be stored in HttpsOnly cookies so that requests can be authenticated for each request
json token is used it is fast and easy for the server to decrypt. 

Api Gateway
its job is to route the request to the correct microservices and validate whether they have the correct
permission as it too decrypts json token it uses the Eureka server to route to the correct microservice.

LClass service discovery
Keeps all microservices registered so that Api Gateway can call it.

the project is still going on changes will be made.
