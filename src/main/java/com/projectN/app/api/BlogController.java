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

import com.projectN.app.exception.BlogCollectionException;
import com.projectN.app.model.Post;
import com.projectN.app.service.BlogService;

@RestController
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/posts")
	public ResponseEntity<?> getAllPosts() {
		List<Post> posts = blogService.getAllPosts();
		if (posts.size() > 0) {
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No posts available", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/posts")
	public ResponseEntity<?> createPost(@RequestBody Post post) {
		try {
			blogService.createPost(post);
			return new ResponseEntity<Post>(post, HttpStatus.OK);
		} catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch(BlogCollectionException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		 }
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<?> getSinglePost(@PathVariable("id") String id) {
		try {
			Post singlePost = blogService.getSinglePost(id);
			return new ResponseEntity<>(singlePost, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/posts/{id}")
	public ResponseEntity<?> updatePostById(@PathVariable("id") String id,
			@RequestBody Post post) {
		try {
			blogService.updatePost(id, post);
			return new ResponseEntity<>("Update post with ID: " + id + " complete!", HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (BlogCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
		try {
			blogService.deletePostById(id);
			return new ResponseEntity<>("Successfully deleted post with ID: " + id,
					HttpStatus.OK);
		} catch (BlogCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
