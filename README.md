## Employee Service

The present service is built to handle all the employee domain operations like adding a new employee, logical deletion, update employee and retrieve employees using pagination.

## Stack Tech:
- Java version: 17
- Maven version: 3.*
- Spring Boot version: 3.3.3

## OpenAPI
- APIs are documented using openAPI 3 version

## Run

To run the service please provide the next argument to the VM:

If you're going to run it locally using H2 DB use:

    -Dspring.profiles.active=DEV

If you're going to run it locally using a external DB use:
    
    -Dspring.profiles.active=UAT

## Environment
We include two properties files
    
    1- application.properties file:
        -environment name: DEV
        -It is ready to all dev/local test/enhancenmets
        -It uses a memory database(H2)
        -Every time that you run the project, it create the table employee and populate with data, 1000 records

    2- application-uat.properties file:
        
        **If you follow this approach please install MySQL Community Server 8.0.41 and MySQL Workbench 8.0.41

        -environment name: UAT (user acceptance testing)
        -It is prepare to connect to a MySQL Database
        -You can use the schema.sql file to create the database and table using the MySQL Workbench tool
        -Use the data.sql to populate the database

## Unit Test
Unit test are provided on the test directory

## curl Commands
A postman collection is provided

    -src/main/resources/Rebboot.postman_collection.json
