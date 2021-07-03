package com.projectN.app.exception;

public class UserCollectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserCollectionException(String message) {
		super(message);
	}
	
	public static String UserNotFoundException(String id) {
		return "User with ID: "+ id +" not found!";
	}
	
	public static String UserAlreadyExists() {
		return "User with given username already exists!";
	}

}
