package cn.xdevops.controller;

import cn.xdevops.entity.Employee;
import cn.xdevops.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("")
    public List<Employee> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @PutMapping("/{id}")
    public Employee createOrUpdateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return employeeService.saveOrUpdateEmployee(newEmployee, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }
}
