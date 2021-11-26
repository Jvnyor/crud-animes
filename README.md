# Rest API to register Users with authentication in Spring Boot

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Querys MySQL

Show columns and values of database created:

`SELECT * FROM users.user;`

## Requests

http://localhost:8080/ - URL Tomcat server

/api/user - Return list of all Users - GET 

/api/user/{id} - Return a User by Id - GET

/api/user/?name={name} - Return a User by name - GET

/api/ - Register User - POST

/api/admin/{id} - Delete User by Id - DELETE

<!--/api/user - Update User by Id - PUT-->

### Use to requests Postman or Swagger UI (Swagger requires authentication).

