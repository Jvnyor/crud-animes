# Rest API to create Animes list and register Users for authentication with Spring Boot

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Requests

http://localhost:8080/ - URL Tomcat server

### Requests authentication

/registration - Registration of User - POST

### CURL

```
curl --location --request POST 'localhost:8080/registration' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "FirstName",
    "lastName": "LastName",
    "USERNAME": "Username",
    "password": "password"
}'
```

/login - Login page - GET

### API of animes requests

/api/animes/user/ - Return list paginated of all Animes - GET 

/api/animes/user/all - Return list of all Animes - GET 

/api/animes/user/{id} - Replace a Anime by Id - PUT

/api/animes/user/{id} - Return a Anime by Id - GET

/api/animes/user/find?name={name} - Return a Anime by name - GET

/api/animes/user/{id} - Delete Anime by Id - DELETE

### API of users requests

/api/users/admin/ - GET

/api/users/admin/{id} - GET

/api/users/admin/{id} - DELETE

/api/users/admin/{id} - PUT

### Use to requests Postman or Swagger UI (Swagger requires authentication).
