package com.projectN.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectN.app.exception.UserCollectionException;
import com.projectN.app.model.User;
import com.projectN.app.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public void createUser(User user) throws ConstraintViolationException, UserCollectionException {
		Optional<User> userOptional =  userRepo.findUserByUsername(user.getUsername());
		
		if (userOptional.isPresent()) {
			throw new UserCollectionException(UserCollectionException.UserAlreadyExists());
		} else {
			user.setCreatedAt(LocalDateTime.now());
			user.setUpdatedAt(LocalDateTime.now());
			userRepo.save(user);
		}
		
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepo.findAll();
		
		if (users.size() > 0) {
			return users;
		} else {
			return new ArrayList<User>();
		}
	}

	@Override
	public User getSingleUser(String id) throws UserCollectionException {
		Optional<User> optionalUser = userRepo.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserCollectionException(UserCollectionException.UserNotFoundException(id));
		} else {
			return optionalUser.get();		
		}
	}

	@Override
	public void updateUser(String id, User user) throws UserCollectionException {
		Optional<User> userWithId = userRepo.findById(id);
		if (userWithId.isPresent()) {
			User userToUpdate = userWithId.get();
			userToUpdate.setFirstname(user.getFirstname());
			userToUpdate.setLastname(user.getLastname());
			userToUpdate.setPassword(user.getPassword());
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setAbout(user.getAbout());
			userToUpdate.setUpdatedAt(LocalDateTime.now());
		} else {
			throw new UserCollectionException(UserCollectionException.UserNotFoundException(id));
		}
		
	}

	@Override
	public void deleteUserById(String id) throws UserCollectionException {
		Optional<User> optionalUser = userRepo.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserCollectionException(UserCollectionException.UserNotFoundException(id));
		} else {
			userRepo.deleteById(id);		
		}
		
	}

}
