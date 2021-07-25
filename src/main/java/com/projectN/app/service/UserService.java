package com.projectN.app.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

import com.projectN.app.exception.UserCollectionException;
import com.projectN.app.model.User;

public interface UserService {

public void createUser(User user) throws ConstraintViolationException, UserCollectionException;
	
	public List<User> getAllUsers();
	
	public User getSingleUser(String id) throws UserCollectionException;
	
	public User getUserByUsername(String username) throws UserCollectionException;
	
	public void updateUser(String id, User user) throws UserCollectionException;
	
	public void updateUserByUsername(String username, User user) throws UserCollectionException;
	
	public void deleteUserById(String id) throws UserCollectionException;
}
