<!-- ABOUT THE PROJECT -->
## About The Project

<p>Service created as part of hostfully evaluation.</p> 

This project has been built upon MVC. The Backend’s most used, successful and traditional architecture.
This architecture was chosen because it's easy do develop in parallel and in a rapid manner. 

Sequence diagrams has been included bellow describing the flows to provide a good understanding about classes behavior and communication.
* [booking](src/main/resources/images/bookProperty.svg)
* [updateBooking](src/main/resources/images/updateBookProperty.svg)
* [cancelBooking](src/main/resources/images/cancelBookProperty.svg)
* [rebook](src/main/resources/images/rebookProperty.svg)
* [cancelBooking](src/main/resources/images/cancelBookProperty.svg)
* [deleteBooking](src/main/resources/images/deleteBookProperty.svg)
* [blockProperty](src/main/resources/images/blockProperty.svg)
* [updateBlockProperty](src/main/resources/images/updateBlockProperty.svg)
* [deleteBlockProperty](src/main/resources/images/deleteBlockProperty.svg)
* [updateGuest](src/main/resources/images/updateGuest.svg)


### Database
ERM diagram can be found [here](src/main/resources/ERM_bookingservicedb.png). As suggest H2 is being used as database.

booking-service contains two db files. 
* [schema.sql](src/main/resources/schema.sql):  contains DDL for table creation
* [data.sql](src/main/resources/data.sql): contains SQL insertions with data for testing purposes



#### Technical Stack
* Java 17
* Spring / SpringBoot 3.2
* H2 db

### Endpoints
A postman collection can be found [here](src/main/resources/hostfully_collection) and can be used as request template

### Built With
*  [![Spring-url][Spring.io]][Spring-url]
*  [![Java-url][Java.com]][Java-url]


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Spring-url]: https://spring.io/
[Spring.io]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Java-url]: https://www.java.com/
[Java.com]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=


