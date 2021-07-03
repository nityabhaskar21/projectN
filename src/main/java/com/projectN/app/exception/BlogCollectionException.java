package com.projectN.app.exception;

public class BlogCollectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BlogCollectionException(String message) {
		super(message);
	}
	
	public static String PostNotFoundException(String id) {
		return "Post with ID: "+ id +" not found!";
	}
	
	public static String PostAlreadyExists() {
		return "Post with given title already exists!";
	}

}
