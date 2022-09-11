# Spring Boot Hello World

## Features

Refer to [spring-boot-hello-world](../spring-boot-hello-world)
- Import `spring-boot-starter-validation` dependency
- Validating request body
  - Add validation annotations for entity class
  - Add `@Valid` annotation for request body in methods in the Controller class
  - Customize `MethodArgumentNotValidException` exception handler method
- Validating path variable
  - Add `@Validated` annotation in the Controller class level
  - Add validation annotation for path variable in methods in the Controller class
  - Customize `ConstraintViolationException` exception handler method
- Unit Test for above validation failure cases
  - JUnit5 + Mockito
  - Test the Controller class


## Run application

```bash
mvn spring-boot:run
```

## Test application via command line tool

Use [HTTPie](https://github.com/httpie/httpie) tool to test application

Create a book with invalid input data:
```bash
# miss some required book properties
echo '{"name": "Microservices Patterns"}' \
  | http POST :8080/books
```

By default, return `400 Bad Request` without response body.

After add customized `MethodArgumentNotValidException` exception handler method, the error response as below:
```json
{
    "error": "Bad Request",
    "errors": [
        "Price is mandatory",
        "Author is mandatory"
    ],
    "message": "Validation failure",
    "status": 400,
    "timestamp": "2022-09-10T13:22:50.415+00:00"
}
```

Find a book with invalid book id:
```bash
# 0 < Min(1)
http :8080/books/0
```

The error response:
```json
{
    "error": "Bad Request",
    "message": "findBookById.id: 最小不能小于1",
    "path": "/books/0",
    "status": 400,
    "timestamp": "2022-09-10T13:31:23.892+00:00"
}
```

## Unit Tests

- BookControllerTest

## References

- https://mkyong.com/spring-boot/spring-rest-validation-example/
- https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/
- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web.servlet.spring-mvc.error-handling
