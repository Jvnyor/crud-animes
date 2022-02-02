# Rest API to create Animes

## Requests

https://crud-animes.herokuapp.com/api/animes - URL

https://crud-animes.herokuapp.com/swagger-ui.html - Swagger UI (API Documentation)

#### JSON POST Method:
```
{
    "name":"Death Note"
}
```

#### Path's:

/api/animes - Return list paginated of all Animes - GET 

/api/animes - Create a Anime - POST

/api/animes/all - Return list of all Animes - GET 

/api/animes/{id} - Replace a Anime by Id - PUT

/api/animes/{id} - Return a Anime by Id - GET

/api/animes/find?name={name} - Return a Anime by name - GET

/api/animes/{id} - Delete Anime by Id - DELETE

### Use to requests Postman or Swagger UI (in production).

## Tests

#### Unit Tests and Integration tests

*src/main/test/java/br/com/josias/animes/*
