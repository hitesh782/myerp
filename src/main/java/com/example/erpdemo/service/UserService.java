package com.example.erpdemo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.Exception.UserException;
import com.example.erpdemo.model.User;
import com.example.erpdemo.model.query.SearchEvent;

public interface UserService {
	List<User> getAllUsers();

	User createUser(User user) throws UserException;

	User updateUser(String userId, User user) throws UserException;

	void deleteUser(String userId) throws UserException;

	Page<User> search(SearchEvent searchEvent, Pageable pageable) throws SearchException;
}
