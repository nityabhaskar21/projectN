package com.projectN.app.api;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectN.app.exception.UserCollectionException;
import com.projectN.app.model.User;
import com.projectN.app.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (users.size() > 0) {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		try {
			userService.createUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch(UserCollectionException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		 }
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getSinglePost(@PathVariable("id") String id) {
		try {
			User singleUser = userService.getSingleUser(id);
			return new ResponseEntity<>(singleUser, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable("id") String id,
			@RequestBody User user) {
		try {
			userService.updateUser(id, user);
			return new ResponseEntity<>("Update user with ID: " + id + " complete!", HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (UserCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
		try {
			userService.deleteUserById(id);
			return new ResponseEntity<>("Successfully deleted user with ID: " + id,
					HttpStatus.OK);
		} catch (UserCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
