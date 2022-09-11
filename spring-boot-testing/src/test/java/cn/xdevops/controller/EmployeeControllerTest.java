package cn.xdevops.controller;

import cn.xdevops.entity.Employee;
import cn.xdevops.exception.EmployeeNotFoundException;
import cn.xdevops.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("should create a new employee")
    void shouldCreateANewEmployee() throws Exception {
        Employee newEmployee = new Employee(1L, "Bruce", "Lee", "bruce@example.com");
        Mockito.when(employeeService.saveEmployee(newEmployee))
                .thenReturn(newEmployee);

        mockMvc.perform(post("/employees")
                .content(om.writeValueAsString(newEmployee))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(newEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(newEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(newEmployee.getEmail())));
    }

    @Test
    @DisplayName("should find a book by id")
    void shouldFindABookById() throws Exception {
        Long id = 1L;
        Employee employee = new Employee(id, "Bruce", "Lee", "bruce@example.com");
        Mockito.when(employeeService.findEmployeeById(id))
                .thenReturn(employee);

        mockMvc.perform(get("/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("should can not find a employee with an invalid id")
    void shouldCanNotFindAEmpolyeeWithAnInvalidId() throws Exception {
        Long invalidId = 5L;
        Mockito.when(employeeService.findEmployeeById(invalidId))
                .thenThrow(new EmployeeNotFoundException(invalidId));

        // need ensure EmployeeNotFoundException is captured and handled
        mockMvc.perform(get("/employees/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(employeeService).findEmployeeById(invalidId);
    }

    @Test
    @DisplayName("should find all employees")
    void shouldFindAllEmployees() throws Exception {
        Employee employee1 = new Employee(1L, "Bruce", "Lee", "bruce@example.com");
        Employee employee2 = new Employee(2L, "James", "Greeting", "james@example.com");
        List<Employee> employeeList = List.of(employee1, employee2);
        when(employeeService.findAllEmployees())
                .thenReturn(employeeList);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employeeList.size()))
                .andExpect(jsonPath("$[0].firstName", is(employee1.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(employee1.getLastName())))
                .andExpect(jsonPath("$[0].email", is(employee1.getEmail())))
                .andExpect(jsonPath("$[1].firstName", is(employee2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(employee2.getLastName())))
                .andExpect(jsonPath("$[1].email", is(employee2.getEmail())));
    }

    @Test
    @DisplayName("should update an employee")
    void shouldUpdateAnEmployee() throws Exception {
        Long id = 1L;
        Employee newEmployee = new Employee(id, "Berry", "Wow", "berry@example.com");

        when(employeeService.saveOrUpdateEmployee(newEmployee, id))
                .thenReturn(newEmployee);

        mockMvc.perform(put("/employees/{id}", id)
                .content(om.writeValueAsString(newEmployee))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(newEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(newEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(newEmployee.getEmail())));
    }

    @Test
    @DisplayName("should delete an employee by id")
    void shouldDeleteAnEmployeeById() throws Exception {
        // mock void method
        Long id = 1L;
        doNothing().when(employeeService).deleteEmployeeById(id);

        mockMvc.perform(delete("/employees/{id}", id))
                .andExpect(status().isOk());

        // ensure the mocked method is called
        verify(employeeService).deleteEmployeeById(id);
    }
}