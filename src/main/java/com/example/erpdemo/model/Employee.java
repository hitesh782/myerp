package com.example.erpdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private String id;

    @NotBlank(message = "Employee name is mandatory")
    @Size(min = 3, max = 100, message = "Employee name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Employee description is mandatory")
    @Size(max = 500, message = "Employee description must not exceed 500 characters")
    private String description;
}
