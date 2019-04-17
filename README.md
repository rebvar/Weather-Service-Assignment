This repository contains the source code for the location weather task. The project is implemenmted according to the specifications provided. The implementation also extends the requirements by adding new features, beside the requirements. The following contains the implementation details.


# Implementation details and tools

## Development Environment
I used the Spring tools suite for implementing this project. The spring tool suite provides a convinient development environment based on eclipse which is familiar and easy to use. 

## Production Database
I used a MySql database for production purposes. The application.properties file in resources, must contain the username, password and connection string, including the database name. The specified user must have suitable permissions.

## Testing Database
I used H2 in memory database for testing purposes. I am aware of few differences and potential inconsistensies. However, with standard coding practices, those concerns would be minimal, and of no importance for this particular task. The application-test.properties file contains the options required for a H2 in memory database. 

## Port and Run
The application run details are provided in application.properties file which include the port number and the context path. The backend has the following options.

```
server.servlet.context-path=/nordea-task-ws
server.port=8080
```


# Backend Components
The application is comprised of two main components. These include UserXXX and LocationWeatherXXX classes/entities/services/etc. 
I have tried to keep the code clean with meaningful names and structure, to minimize the requirements for excessive documentations. 

For each of the above components, the corresponding Entities, DTOs, Response Modesl, Repositories and Services are Created. 


# Backend Security

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

# Weather API

## Search

Weather queries can be sent to the rest api endpoint /weather in the following formats: 
```
/weather/lat/{lat}/lon/{lon}
/weather/lon/{lon}/lat/{lat}
/weather/city/{city}
```

All successful search queries are stored in the database. The next part explains the save data structure.

## Users and Anonymous Weather Searches
Both logged in users as well as anonymous users can use the service for weather information. All of the searches by logged in users are stored in location_weather (LocationWeatherEntity) table. Each entry in this table, holds a link to the user that has made the search. 

Anonymous searches are stored in a separate table, called anonym_location_weather(AnonymLocationWeatherEntity). The data in this table do not have user links. They instead have two other fields for storing browser info and ip address. These fields are not populated and are here just for demonstration purposes.

## Favorites
Each weather search in the location_weather table has a field called isFavorite, of type boolean, which shows if this particular query is selected as favorite. To remove/add item to favorites, the api flips this field to the other boolean value. 

## Forecasts

Forecasts are stored separately for logged in and anonym users. The reason for this decision is that the foreign tables are not the same. Therefore, two user and anonym searches, have a table each, to store the forecasts.


# Front End 
The frontend runs in a separate port specified in the application.properties file of the frontend.

```
server.port=9980
```

## Security 
The front end operations pass the auth token with each request if the user has logged in. For the loggin process, I have persisted the token in cookies and read the cookie on the requests. In sigout, the cookie is cleared. The tokens are specified to be valid for a day in AppConstants.


# Tests 

Relevant JUnit tests are specified as examples of testing. For this, I have used Mockito for dependency injection. The repositories are tested using the H2 in memory database. 
The services are tested using Mockito injection approach. I have provided a test suite for the assignment. 
The test suite is small at this stage. I have not implemented a test for every single part of the code. The reason for this is the lack of time and will be implemented if required. Current implementations, are there to show my understanding of the frameworks and methods. 

# Impelemnted Features of the Assignment 

The implemeted requirements are marked with - [x] icon.

General Requirements:

- [x] Frontend and backend must be runnable locally from command line.
- [x] Frontend and backend must be different instances and run separately in different ports.
- [x] You are free to utilise any free frameworks, libraries and templates to construct project structures, backend skeleton and client UI. As long as actual business code, UI and logic parts are written by yourself.
- [x] Implement relevant tests.
- [x] Micro service architecture, suitable design patterns and best REST practises should be followed/utilised whenever possible.
- [x] The GIT repository used to return the exercise must also contain README.md file which explains all the steps to run the web client and the service.
 

Rough illustration of the data flow:

      Web frontend     <-[JSON]->     Your REST API service      <-[any other format than JSON]-      Some free weather API service

 

Backend requirements:

- [x] Maven, Spring Boot (tip: https://start.spring.io/) and Java 8 must be used.
- [x] REST API to support actions on the front-end.
- [x] Responses towards the frontend always in JSON.
- [x] Search results can be fetched from any external (free) weather APIs that return the data in any other format than JSON.
 

Frontend requirements:

- [x] Front-end can be as simple as possible without any extra eye candy.
- [x] User must be able to search weather for given location.
- [x] User must see the search result.
- [x] Today’s weather information is displayed for the search results. At least current temperature.
 

Bonus:

- [ ] Apply some reactive programming framework in your solution, such as RxJava (http://reactivex.io/) or Reactor (https://projectreactor.io/ )
- [x] More detailed current day information and weather predictions for next few days
- [x] Authorization on any API call
- [x] UI 2.0 features – Favourites (can be handled in memory so no persistent storage needed):
- [x] User can save the search as a favourite.
- [x] User can see all the favourite searches.
- [x] User can delete favourite search(es).
- [x] User should be able to retrigger the search from any of the favourites.
- [x] User is authorized to save/see/delete favourites in frontend.
- [ ] Fancy UI
