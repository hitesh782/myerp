package com.example.erpdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.Exception.UserException;
import com.example.erpdemo.model.User;
import com.example.erpdemo.model.query.SearchEvent;
import com.example.erpdemo.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping()
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/criteria-search")
	public Page<User> search(@RequestBody final SearchEvent searchEvent, final Pageable pageable)
			throws SearchException {
		return userService.search(searchEvent, pageable);
	}

	@PostMapping()
	public User createUser(@RequestBody User user) throws UserException {
		return userService.createUser(user);
	}

	@PutMapping()
	public User updateUser(@PathVariable String userId, @RequestBody User user) throws UserException {
		return userService.updateUser(userId, user);
	}

	@DeleteMapping()
	public void deleteUser(@PathVariable String userId) throws UserException {
		userService.deleteUser(userId);
	}
}
