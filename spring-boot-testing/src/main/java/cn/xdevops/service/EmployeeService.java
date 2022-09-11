package cn.xdevops.service;

import cn.xdevops.entity.Employee;
import cn.xdevops.exception.EmployeeNotFoundException;
import cn.xdevops.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveOrUpdateEmployee(Employee newEmployee, Long id) {
        return employeeRepository.findById(id)
                .map(e -> {
                    // update existing employee
                    e.setFirstName(newEmployee.getFirstName());
                    e.setLastName(newEmployee.getLastName());
                    e.setEmail(newEmployee.getEmail());
                    return employeeRepository.save(e);
                })
                .orElseGet(() -> {
                    // create a new employee
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                });
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
