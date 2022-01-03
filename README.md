# Rest API to create Animes list and register Users for authentication with Spring Boot

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Requests

http://localhost:8080/ - URL Tomcat server

/api/animes/user/ - Return list paginated of all Animes - GET 

/api/animes/user/all - Return list of all Animes - GET 

/api/animes/user/{id} - Replace a Anime by Id - PUT

/api/animes/user/{id} - Return a Anime by Id - GET

/api/animes/user/find?name={name} - Return a Anime by name - GET

/api/animes/user/{id} - Delete Anime by Id - DELETE

## Requests authentication

/api/animes/registration - Registration of User - POST
### Use to requests Postman or Swagger UI (Swagger requires authentication).
