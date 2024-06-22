package com.example.erpdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.erpdemo.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
