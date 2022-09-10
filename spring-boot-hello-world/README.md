# Spring Boot Hello World

## Features

- Simple CRUD application via JPA and H2
- REST API
    - Create a new book
        - Customize HTTP status code as `CREATED`
    - Find a book by id
    - Find all books
    - Create or update a book
    - Delete a book
- Error Handling
    - Customize exception
    - Global exception handler
    - Remove `trace` info from error response
    

## Run application

```bash
mvn spring-boot:run
```

## Test application via command line tool

Use [HTTPie](https://github.com/httpie/httpie) tool to test application

Create a book:
```bash
# first book
echo '{"name": "Spring Boot Hello World", "author":"William", "price": 8.88}' \
  | http POST :8080/books

# another books
echo '{"name": "REST API design", "author":"Martin", "price": 9.99}' \
  | http POST :8080/books
```

Find a book via id:
```bash
# book id = 1
http :8080/books/1

# book id = 2
http :8080/books/2

# book not found
http :8080/books/5
```

Book not found error response:
```json
{
    "error": "Not Found",
    "message": "Boot not found via id: 5",
    "path": "/books/5",
    "status": 404,
    "timestamp": "2022-09-10T12:13:44.765+00:00"
}
```

Update a book:
```bash
echo '{"name": "REST API design second edition", "author":"Martin", "price": 10.5}' \
  | http PUT :8080/books/2
```

```json
{
    "author": "Martin",
    "id": 2,
    "name": "REST API design second edition",
    "price": 10.5
}
```

Find all books:
```bash
http :8080/books
```

Delete a book:
```bash
http DELETE :8080/books/1
```

## References

- https://mkyong.com/spring-boot/spring-rest-hello-world-example/
- [HTTPie Cheatsheet](https://devhints.io/httpie)