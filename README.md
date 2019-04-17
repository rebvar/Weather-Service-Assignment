This repository contains the source code for the location weather task. The project is implemenmted according to the specifications provided. The implementation also extends the requirements by adding new features, beside the requirements. The following contains the implementation details.


# Implementation details and tools

## Development Environment
I used the Spring tools suite for implementing this project. The spring tool suite provides a convinient development environment based on eclipse which is familiar and easy to use. 

## Production Database
I used a MySql database for production purposes. The application.properties file in resources, must contain the username, password and connection string, including the database name. The specified user must have suitable permissions.

## Testing Database
I used H2 in memory database for testing purposes. I am aware of few differences and potential inconsistensies. However, with standard coding practices, those concerns would be minimal, and of no importance for this particular task. The application-test.properties file contains the options required for a H2 in memory database. 


# Components
The application is comprised of two main components. These include UserXXX and LocationWeatherXXX classes/entities/services/etc. 
I have tried to keep the code clean with meaningful names and structure, to minimize the requirements for excessive documentations. 

For each of the above components, the corresponding Entities, DTOs, Response Modesl, Repositories and Services are Created. 


# Security

The spring security framework is used to access to the rest endpoints. The rest API has multiple open endpoints to login and register. Beside these two, anonymous searches are also allowed and their endpoints allow access without logging in. 

# External Weather API

As recommended by the assignment, I have used a free external weather API. In this case, I used openweathermap.org 

Two methods of searches are used in this service and search by coordinates.
The address for this operations is specified in application.properties file as follows
```
base_external_url=https://api.openweathermap.org/data/2.5/weather?
```



for the forecast data, the following address is used, which is specified in the application.properties file as well. This request returns 5 days / 3 hour forecast data. 

```
forecast_external_url=https://api.openweathermap.org/data/2.5/forecast?
```

