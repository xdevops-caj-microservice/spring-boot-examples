# Spring Boot Testing

## Test Tools

- JDK
  - Java 11+
- Build Tool
  - Maven
- Dev Framework
  - Spring Boot
  - [Lombok](https://projectlombok.org/features/)
- Unit Framework, Mock and Assertion
  - JUnit 5 Framework
  - Hamcrest
  - [AssertJ Core](https://assertj.github.io/doc/)
  - JsonPath
  - Mockito
- Maven Plugins
  - [maven-site-plugin](https://maven.apache.org/plugins/maven-site-plugin/)
  - [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
  - [maven-surefire-report-plugin](https://maven.apache.org/surefire/maven-surefire-report-plugin/)
  - [maven-jxr-plugin](https://maven.apache.org/jxr/maven-jxr-plugin/)
  - [maven-failsafe-plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/)
- IDE
  - IntelliJ IDEA
  - [Spring Tools](https://spring.io/tools)
    
## Mock Annotations

Plain Mockito library
- `@Mock` // mock an object
- `Mockito.mock()` // mock an object, mock a method
- `@RunWith(MockitoJUnitRunner.class` // enable Mockito annotations in the test class
- `@Injectmocks` // inject `@Mock` mocked objects into this main mocked object

Spring Boot library wrapping Mockito library
- `@MockBean` // mock a bean in spring boot test application text


References
- https://stackoverflow.com/questions/44200720/difference-between-mock-mockbean-and-mockito-mock
- https://rieckpil.de/difference-between-mock-and-mockbean-spring-boot-applications/


## Layered Testing

- Test repository layer:
  1. Use `@DataJpaTest` to test repository layer
  2. Use `@TestMethodOrder` and `@Order` to run test methods in order in the test class
  3. Use `@Rollbak(value = false)` to disable roll back data (commit data changes into database)
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
- Integration test
  TBC with Testcontainers

Unit Test class examples:
- Test Repository Layer
  - EmployeeRepositoryTest
- Test Service Layer
  - EmployeeServiceTest
- Test Controller Layer
  - EmployeeControllerTest

Integration Test
  - TBC
  
## Maven Commands

Generate XML Unit Test reports under `target/surefire-reports`:
```bash
mvn clean test
```

Generate HTML Unit Test reports as `target/site/surefire-report.html`:
```bash
mvn clean site
```
Notes:
- Use `maven-surefire-report-plugin` to generate HTML Unit Test reports
- Use `maven-jxr-plugin` for Source Code Reference for Failed Tests
- Require `maven-site-plugin` to support nice css rendering
- Disable `maven-project-info-reports-plugin` to generate project information to save a lot of time!

References:
- https://maven.apache.org/surefire/maven-surefire-report-plugin/index.html
- https://maven.apache.org/surefire/maven-surefire-report-plugin/examples/cross-referencing.html
- https://maven.apache.org/plugins/maven-site-plugin/
- https://www.thecodejournal.tech/2021/05/unit-testing-in-maven-junit-html-report/
- https://howtodoinjava.com/junit5/junit-html-report/
- https://stackoverflow.com/questions/35982312/disable-all-reports-from-mavens-project-info-reports-plugin

## References

- [Spring Boot Unit Testing Service Layer using JUnit and Mockito](https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html)
- [CRUD JUnit Tests for Spring Data JPA - Testing Repository Layer](https://www.javaguides.net/2021/07/crud-junit-tests-for-spring-data-jpa.html)