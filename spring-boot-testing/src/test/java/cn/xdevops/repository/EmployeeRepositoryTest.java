package cn.xdevops.repository;

import cn.xdevops.entity.Employee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // must run the whole test class, can't run each test method
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("should save an employee")
    @Order(1)
    @Rollback(value = false)
    void shouldSaveAnEmployee() {
        Employee newEmployee = new Employee(0L, "Bruce", "Lee", "bruce@example.com");

        Employee savedEmployee = employeeRepository.save(newEmployee);

        assertThat(savedEmployee.getId()).isGreaterThan(1L);
        assertThat(savedEmployee).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newEmployee);
    }

    @Test
    @DisplayName("should find an employee by id")
    @Order(2)
    void shouldFindAnEmployeeById() {
        Employee employee = employeeRepository.findById(1L).get();

        assertThat(employee.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("should find all employees")
    @Order(2)
    void shouldFindAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).hasSizeGreaterThan(0);
    }

    @Test
    @DisplayName("should update an employee")
    @Order(4)
    @Rollback(value = false)
    void shouldUpdateAnEmployee() {
        Employee employee = employeeRepository.findById(1L).get();

        employee.setFirstName("William");
        employee.setLastName("Greeting");
        employee.setEmail("william@example.com");

        Employee updatedEmployee = employeeRepository.save(employee);

        assertThat(updatedEmployee).usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    @DisplayName("should delete an employee")
    @Order(5)
    @Rollback(value = false)
    void shouldDeleteAnEmployee() {
        Long id = 1L;
        employeeRepository.deleteById(1L);

        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        assertThat(employeeOptional).isNotPresent();
    }
}