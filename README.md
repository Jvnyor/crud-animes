# Rest API to create Animes list and register Users for authentication with Spring Boot

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Requests

http://localhost:8080/ - URL Tomcat server

/api/v1/animes/user/ - Return list paginated of all Animes - GET 

/api/v1/animes/user/all - Return list of all Animes - GET 

/api/v1/animes/user/{id} - Return a Anime by Id - GET

/api/v1/animes/user/find?name={name} - Return a Anime by name - GET

/api/v1/animes/user/{id} - Delete Anime by Id - DELETE

## Requests authentication

/api/v1/animes/registration - Registration of User - POST
### Use to requests Postman or Swagger UI (Swagger requires authentication).

