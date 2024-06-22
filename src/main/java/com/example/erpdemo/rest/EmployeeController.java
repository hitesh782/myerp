package com.example.erpdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpdemo.Exception.EmployeeException;
import com.example.erpdemo.model.Employee;
import com.example.erpdemo.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<?> createEmployee(@RequestBody Employee Employee) throws EmployeeException {
		Employee createdEmployee = employeeService.createEmployee(Employee);
		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable String id, @Valid @RequestBody Employee Employee)
			throws EmployeeException {
		Employee updatedEmployee = employeeService.updateEmployee(id, Employee);
		return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable String id) throws EmployeeException {
		Employee Employee = employeeService.getEmployeeById(id);
		return new ResponseEntity<>(Employee, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable String id) throws EmployeeException {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
