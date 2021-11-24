# CRUD Rest API Spring Boot

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Querys MySQL

Show columns and values of database created:

`SELECT customer.id,
    customer.name,
    customer.email,
    customer.phone
FROM customers.customer;`

## Requests

http://localhost:8080/ - URL Tomcat server

/customers/ - Return list of all Costumers - GET 

/customers/{id} - Return a Costumer by Id - GET

/customers/?name={name} - Return a Costumer by name - GET

/customers/ - Add a Costumer - POST

/customers/{id} - Delete Costumer by Id - DELETE

/customers/ - Update Costumer by Id - PUT

## Swagger UI

http://localhost:8080/swagger-ui.html - URL to acess Swagger UI in the browser.

