package com.example.erpdemo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.erpdemo.Exception.EmployeeException;
import com.example.erpdemo.Exception.EmployeeNotFoundException;
import com.example.erpdemo.Exception.EmployeeValidationException;
import com.example.erpdemo.model.Employee;
import com.example.erpdemo.repository.EmployeeRepository;
import com.example.erpdemo.service.EmployeeService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private Validator validator;

    @Override
    public Employee createEmployee(Employee employee) throws EmployeeException {
        validateEmployee(employee);
        return employeeRepo.save(employee);
    }

    @Override
    public Employee updateEmployee(String id, Employee employee) throws EmployeeException {
        Optional<Employee> existingEmployee = employeeRepo.findById(id);
        if (!existingEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employee.setId(id); // Ensure the product ID is set
        validateEmployee(employee);
        return employeeRepo.save(employee);
    }

    @Override
    public Employee getEmployeeById(String id) throws EmployeeException {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public void deleteEmployee(String id) throws EmployeeException {
        if (!employeeRepo.existsById(id)) {
            throw new EmployeeNotFoundException("Employee with id is not present: " + id);
        }
        employeeRepo.deleteById(id);
    }

    private void validateEmployee(Employee employee) throws EmployeeException {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Employee> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new EmployeeValidationException(sb.toString());
        }
    }

}
