# Spring Boot Testing

## Test Tools

- JDK
  - Java 11+
- Build Tool
  - Maven
- Dev Framework
  - Spring Boot
  - Lombok
- Unit Framework, Mock and Assertion
  - JUnit 5 Framework
  - Hamcrest
  - [AssertJ Core](https://assertj.github.io/doc/)
  - JsonPath
  - Mockito
- IDE
  - IntelliJ IDEA
    
## Mock Annotations

Plain Mockito library
- `@Mock` // mock an object
- `Mockito.mock()` // mock an object, mock a method
- `@RunWith(MockitoJUnitRunner.class` // enable Mockito annotations in the test class
- `@Injectmocks` // inject `@Mock` mocked objects into this main mocked object

Spring Boot library wrapping Mockito library
- `@MockBean` // mock a bean in spring boot test application text

Typical usage：
- Test service layer: 
  1. Use `@ExtendWith(MockitoExtension.class)` to enable Mockito 
  2. Use `@Mock` to mock repository 
  3. Use `@InjectMocks` to mock service
  4. Use `Mockito.when()` to mock the method
  5. Use `Mockito.verify()` to verity the mocked method is called (mandatory for no return value case)
- Test controller layer
  1. Use `@WebMvcTest(XXController.class)` to only load application context for test
  2. Use `@MockBean` to mock service
  3. Inject `MockMvc` instance
  4. Use `Mockito.when()` to mock the method
  5. Use `mockMvc.perform()` to test controller method
  6. Use `Mockito.verify()` to verity the mocked method is called (mandatory for no return value case)

References
- https://stackoverflow.com/questions/44200720/difference-between-mock-mockbean-and-mockito-mock
- https://rieckpil.de/difference-between-mock-and-mockbean-spring-boot-applications/


## Layered Testing

- Test Service Layer
  - EmployeeServiceTest
- Test Controller Layer
  - EmployeeControllerTest
- Integration Test
  - TBC

## References

- [Spring Boot Unit Testing Service Layer using JUnit and Mockito](https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html)