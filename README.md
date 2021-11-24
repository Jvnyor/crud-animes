# CRUD Rest API Spring Boot

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Querys MySQL

Show columns and values of database created:

`SELECT * FROM customers.customer;`

## Requests

http://localhost:8080/ - URL Tomcat server

/api/user - Return list of all Costumers - GET 

/api/user/{id} - Return a Costumer by Id - GET

/api/user/?name={name} - Return a Costumer by name - GET

/api/ - Add a Costumer - POST

/api/admin/{id} - Delete Costumer by Id - DELETE

/api/admin - Update Costumer by Id - PUT

## Swagger UI

http://localhost:8080/swagger-ui.html - URL to acess Swagger UI in the browser.

