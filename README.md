# Rest API to create Animes

## Database MySQL User Root
- Username: root
- Password: root

*src/main/resources/aplications.properties*

## Requests

http://localhost:8080/ - URL Tomcat server

#### JSON:
```
{
    "firstName":"Firstname",
    "lastName":"Lastname",
    "username":"user",
    "password":"password"
}
```

#### Path's:

<<<<<<< HEAD
/api/animes - Return list paginated of all Animes - GET 
=======
### CURL

```
curl --location --request POST 'localhost:8080/registration' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "FirstName",
    "lastName": "LastName",
    "username": "user",
    "password": "password"
}'
```

/login - Login page - GET
>>>>>>> 195e13f46aa70a59eb66fba6e85651716bd827f5

/api/animes/all - Return list of all Animes - GET 

/api/animes/{id} - Replace a Anime by Id - PUT

/api/animes/{id} - Return a Anime by Id - GET

/api/animes/find?name={name} - Return a Anime by name - GET

/api/animes/{id} - Delete Anime by Id - DELETE

### Use to requests Postman or Swagger UI.
