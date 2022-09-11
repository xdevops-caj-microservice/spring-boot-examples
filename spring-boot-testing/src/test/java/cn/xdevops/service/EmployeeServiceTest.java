package cn.xdevops.service;

import cn.xdevops.entity.Employee;
import cn.xdevops.exception.EmployeeNotFoundException;
import cn.xdevops.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("should create a new employee")
    void shouldCreateANewEmployee() {
        Employee newEmployee = new Employee(1L, "Bruce", "Lee", "bruce@example.com");
        when(employeeRepository.save(newEmployee))
                .thenReturn(newEmployee);

        Employee savedEmployee = employeeService.saveEmployee(newEmployee);

        // compare two objects
        // https://www.javadoc.io/static/org.assertj/assertj-core/3.23.1/org/assertj/core/api/AbstractObjectAssert.html#method.summary
        assertThat(savedEmployee).usingRecursiveComparison()
                .isEqualTo(newEmployee);
    }

    @Test
    @DisplayName("should find an employee by id")
    void shouldFindAnEmployeeById() {
        Long id = 1L;
        Employee employee = new Employee(id, "Bruce", "Lee", "bruce@example.com");
        when(employeeRepository.findById(id))
                .thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.findEmployeeById(id);

        // compare two objects
        assertThat(foundEmployee).usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    @DisplayName("should failed to find an employee with an invalid id")
    void shouldFailedToFindAnEmployeeWithAnInvalidId() {
        Long invalidId = 5L;
        when(employeeRepository.findById(invalidId))
                .thenReturn(Optional.empty());

        // assert thrown exception
        assertThatThrownBy(() -> {
            employeeService.findEmployeeById(invalidId);
        }).isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    @DisplayName("should find all employees")
    void shouldFindAllEmployees() {
        Employee employee1 = new Employee(1L, "Bruce", "Lee", "bruce@example.com");
        Employee employee2 = new Employee(2L, "James", "Greeting", "james@example.com");
        List<Employee> employeeList = List.of(employee1, employee2);
        when(employeeRepository.findAll())
                .thenReturn(employeeList);

        List<Employee> foundEmployees = employeeService.findAllEmployees();

        // compare two lists
        assertThat(foundEmployees).containsExactlyElementsOf(employeeList);
    }

    @Test
    @DisplayName("should update an existing employee")
    void shouldUpdateAnExistingEmployee() {
        Long id = 1L;
        Employee originalEmployee = new Employee(id, "Bruce", "Lee", "bruce@example.com");
        Employee newEmployee = new Employee(id, "Berry", "Wow", "berry@example.com");

        when(employeeRepository.findById(id))
                .thenReturn(Optional.of(originalEmployee));
        when(employeeRepository.save(newEmployee))
                .thenReturn(newEmployee);

        Employee updatedEmployee = employeeService.saveOrUpdateEmployee(newEmployee, id);

        // compare two objects
        assertThat(updatedEmployee).usingRecursiveComparison()
                .isEqualTo(newEmployee);
    }

    @Test
    @DisplayName("should create a new employee when try to update a new employee")
    void shouldCreateANewEmployeeWhenTryToUpdateANewEmployee() {
        Long id = 1L;
        Employee newEmployee = new Employee(id, "Berry", "Wow", "berry@example.com");

        when(employeeRepository.findById(id))
                .thenReturn(Optional.empty());
        when(employeeRepository.save(newEmployee))
                .thenReturn(newEmployee);

        Employee updatedEmployee = employeeService.saveOrUpdateEmployee(newEmployee, id);

        // compare two objects
        assertThat(updatedEmployee).usingRecursiveComparison()
                .isEqualTo(newEmployee);
    }

    @Test
    @DisplayName("should delete an employee by id")
    void shouldDeleteAnEmployeeById() {
        // mock void method
        Long id = 1L;
        doNothing().when(employeeRepository).deleteById(id);

        employeeService.deleteEmployeeById(id);

        // verify the mocked method is called
        verify(employeeRepository).deleteById(id);
    }
}