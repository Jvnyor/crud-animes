# Rest API to create Animes list and register Users for authentication with Spring Boot

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Requests

http://localhost:8080/ - URL Tomcat server

/api/api/v1/user/animes - Return list of all Animes - GET 

/api/api/v1/user/animes/{id} - Return a Anime by Id - GET

/api/v1/user/animes/?name={name} - Return a Anime by name - GET

/api/v1/user/animes/{id} - Delete Anime by Id - DELETE

## Requests authentication

/api/v1/register - Register User - POST
### Use to requests Postman or Swagger UI (Swagger requires authentication).

