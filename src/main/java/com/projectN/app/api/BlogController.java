package com.projectN.app.api;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.projectN.app.util.Constants.DATA;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectN.app.exception.BlogCollectionException;
import com.projectN.app.exception.UserCollectionException;
import com.projectN.app.model.Post;
import com.projectN.app.service.BlogService;

@RestController
public class BlogController {

	@Autowired
	private BlogService blogService;

	@GetMapping("/")
	public ResponseEntity<?> listAllApi() {
		Map<String, String> apiMap = new LinkedHashMap<>();
		apiMap.put("GET all posts", "/posts");
		apiMap.put("GET post by ID", "/posts/{id}");
		apiMap.put("POST a post", "/posts");
		apiMap.put("PUT a post by ID", "/posts/{id}");
		apiMap.put("DELETE a post by ID", "/posts/{id}");

		return new ResponseEntity<>(apiMap, HttpStatus.OK);

	}

	@GetMapping("/posts")
	public ResponseEntity<?> getAllPosts() {
		List<Post> posts = blogService.getAllPosts();
		if (!posts.isEmpty() ) {
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No posts available", HttpStatus.NOT_FOUND);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/posts/page")
	public ResponseEntity<?> getAllPostsByPage(@RequestParam(value = "pageno", defaultValue = "0") int pageNo,
			@RequestParam(value = "pagesize", defaultValue = "2") int pageSize) {
		Map<String, Object> posts = blogService.getAllPostsInPage(pageNo, pageSize);
		if (posts.size() > 0) {
			return new ResponseEntity<List<Post>>((List<Post>) posts.getOrDefault(DATA, Arrays.asList()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No posts available", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/posts")
	public ResponseEntity<?> createPost(@RequestBody Post post) throws UserCollectionException {
		try {
			blogService.createPost(post);
			return new ResponseEntity<Post>(post, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (BlogCollectionException e) {
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
	public ResponseEntity<?> updatePostById(@PathVariable("id") String id, @RequestBody Post post)
			throws UserCollectionException {
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
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) throws UserCollectionException {
		try {
			blogService.deletePostById(id);
			return new ResponseEntity<>("Successfully deleted post with ID: " + id, HttpStatus.OK);
		} catch (BlogCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
