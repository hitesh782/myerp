package com.example.erpdemo.service;

import java.util.List;

import com.example.erpdemo.Exception.EmployeeException;
import com.example.erpdemo.model.Employee;

public interface EmployeeService {

    Employee createEmployee(Employee employee) throws EmployeeException;

    Employee updateEmployee(String id, Employee employee) throws EmployeeException;

    Employee getEmployeeById(String id) throws EmployeeException;

    List<Employee> getAllEmployees();

    void deleteEmployee(String id) throws EmployeeException;

}
