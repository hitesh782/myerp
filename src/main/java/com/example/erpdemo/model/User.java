package com.example.erpdemo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.erpdemo.model.bo.BOWithId;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BOWithId {

	@NotBlank(message = "User Name is mandatory")
	private String userName;

	@NotBlank(message = "Password is mandatory")
	private String password;

	private Cart cart;

}
