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

/api/user - Return list of all Customer Users - GET 

/api/user/{id} - Return a Customer User by Id - GET

/api/user/?name={name} - Return a Customer by name - GET

/api/ - Register Customer User - POST

/api/admin/{id} - Delete Customer User by Id - DELETE

/api/admin - Update Customer User byoId - PUT

## Swagger UI

http://localhost:8080/swagger-ui.html - URL to acess Swagger UI in the browser.

