
# food-ordering-system

Project from learning course in which utilizing best practises of building microservices while applying Clean and Hexagonal Architectures and using Domain Driven Design.

Key concepts that were applied during the development process:

 - Spring boot microservices
 - Clean Architecture
 - Hexagonal Architecture
 - Domain Driven Design
 - Event-driven services using Apache Kafka
 - SAGA Architecture Pattern
 - Outbox Architecture Pattern
 - CQRS Architecture Pattern
 - Kubernetes on local using Docker desktop
 - Kubernetes on Google Cloud using Google Kubernetes Engine(GKE)

 

## Appendix

Project developed based on spring boot. Base components - 4 microservices developed using Clean and Hexagonal architecture principles. 
To communicate with the data stores in microservices used Spring Data JPA for PostgreSQL.
Also used Apache Kafka as the event store, and events to communicate between services, and to implement the architectural patterns.
Eventually, to apply automate deployment, scaling and managing in this project was used Docker along with Kubernetes  

## Tech Stack

Java 17, Spring Boot:2.7.0(WEB, JPA, Kafka, Tx, Validation), Kafka as the Event Store, PostgreSQL, Docker, 


