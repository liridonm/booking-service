# Booking Service

## Description

Simple service of booking a property written in spring boot.

## Goals

- [x] Crud Operations for Bookings
- [x] Crud Operations for Blocks
- [x] Rebooking option
- [x] Booking Overlaps
- [x] Distributed Locking


## Running

```shell
mvn clean install -DskipTests
mvn spring-boot:run
```

## Summary

### Domain:

Within this project, two distinct models are utilized: Property and Booking. These models maintain a One-to-Many relationship, signifying that a singular Property may accommodate multiple Bookings.

Bookings encompass two primary types: `DEFAULT` and `BLOCK`. DEFAULT serves as the standard reservation method utilized by clients to secure a property for their stay. Conversely, BLOCK represents a specialized booking type employed by property owners or managers to designate periods during which no guest bookings are permissible.

### Distributed Locking:
In scenarios where concurrent actions by multiple users may result in conflicting booking requests, the implementation leverages RedissonClient in conjunction with Redis to facilitate distributed locking. This mechanism ensures that all backend instances can access a shared lock, thus mitigating the risk of conflicting operations.

### Exception Handling:
Exception handling within the project architecture is realized through Spring Rest Controller advice. This framework accommodates two distinct types of exceptions:

1. NotFoundException: Triggered in response to a resource not being found, resulting in the generation of a 404 error within the API.
2. ValidationException: Arises from validation failures, prompting the API to generate a 400 error to signify the occurrence of a client-side issue.

### Standardized API Responses
In alignment with best practices for consistency and maintainability, all API responses within the project adhere to a standardized format encapsulated within the `ResponseEntity<ResponseWrapper>` type.

This approach ensures uniformity across all endpoints, facilitating ease of comprehension and integration for developers interacting with the API. By encapsulating responses within a ResponseWrapper, essential metadata and payload data are structured consistently, enhancing clarity and simplifying error handling processes.