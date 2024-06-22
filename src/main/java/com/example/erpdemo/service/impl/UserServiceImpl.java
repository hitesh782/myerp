package com.example.erpdemo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.Exception.UserException;
import com.example.erpdemo.Exception.UserNotFoundException;
import com.example.erpdemo.Exception.UserValidationException;
import com.example.erpdemo.config.QueryBuilder;
import com.example.erpdemo.model.Cart;
import com.example.erpdemo.model.Product;
import com.example.erpdemo.model.User;
import com.example.erpdemo.model.query.SearchEvent;
import com.example.erpdemo.repository.CartRepository;
import com.example.erpdemo.repository.UserRepository;
import com.example.erpdemo.service.UserService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private Validator validator;

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User createUser(User user) throws UserException {

		validateUser(user);
		User savedUser = userRepo.save(user);

		Cart cart = new Cart();
		cart.setUserId(savedUser.getId());
		cartRepo.save(cart);

		return savedUser;
	}

	@Override
	public User updateUser(String userId, User user) throws UserException {

		Optional<User> userOptional = userRepo.findById(userId);

		if (userOptional.isPresent()) {
			validateUser(user);
			user.setId(userId);
			return userRepo.save(user);
		} else {
			throw new UserNotFoundException("user with given userid is not present");
		}

	}

	@Override
	public void deleteUser(String userId) throws UserNotFoundException {
		Optional<User> userOptional = userRepo.findById(userId);
		if (userOptional.isPresent()) {
			userRepo.deleteById(userId);
		} else {
			throw new UserNotFoundException("user with given userid is not present");
		}

	}

	void validateUser(User user) throws UserValidationException {
		// if (StringUtils.isEmpty(user.getUserName())) {
		// throw new UserValidationException("user name can not be null or empty");
		// } else if (StringUtils.isEmpty(user.getPassword())) {
		// throw new UserValidationException("password can not be null or empty");
		// }

		Set<ConstraintViolation<User>> violations = validator.validate(user);
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<User> violation : violations) {
				sb.append(violation.getMessage()).append("; ");
			}
			throw new UserValidationException(sb.toString());
		}
	}

	@Override
	public Page<User> search(SearchEvent searchEvent, Pageable pageable) throws SearchException {
		Query query = null;
		query = QueryBuilder.buildSearchQuery(searchEvent, pageable);
		query.collation(Collation.of("en").strength(Collation.ComparisonLevel.secondary()));

		return new PageImpl<>(this.mongoOperations.find(query, User.class), pageable,
				this.mongoOperations.count(query, User.class));

	}

}
