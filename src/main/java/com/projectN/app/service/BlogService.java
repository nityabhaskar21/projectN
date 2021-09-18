package com.projectN.app.service;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import com.projectN.app.exception.BlogCollectionException;
import com.projectN.app.exception.UserCollectionException;
import com.projectN.app.model.Post;

public interface BlogService {

	public void createPost(Post post) throws ConstraintViolationException, BlogCollectionException, UserCollectionException;
	
	public List<Post> getAllPosts();
	
	public Map<String, Object> getAllPostsInPage(int pageNo, int pageSize);
	
	public Post getSinglePost(String id) throws BlogCollectionException;
	
	public void updatePost(String id, Post post) throws BlogCollectionException, UserCollectionException;
	
	public void deletePostById(String id) throws BlogCollectionException, UserCollectionException;

}
