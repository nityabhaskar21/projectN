package com.projectN.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectN.app.exception.BlogCollectionException;
import com.projectN.app.exception.UserCollectionException;
import com.projectN.app.model.Post;
import com.projectN.app.model.User;
import com.projectN.app.repo.BlogRepository;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private BlogRepository blogRepo;
	
	@Autowired
	private UserService userService;

	@Override
	public void createPost(Post post) throws ConstraintViolationException, BlogCollectionException, UserCollectionException {
		Optional<Post> postOptional =  blogRepo.findPostByTitle(post.getTitle());
		
		if (postOptional.isPresent()) {
			throw new BlogCollectionException(BlogCollectionException.PostAlreadyExists());
		} else {
			post.setCreatedAt(LocalDateTime.now());
			post.setUpdatedAt(LocalDateTime.now());
			Post postSaved = blogRepo.save(post);
			
			User user = userService.getUserByUsername(postSaved.getUsername());
			List<Post> posts = user.getPosts();
			if (posts == null) {
				posts = new ArrayList<Post>();
			}
			posts.add(postSaved);
			user.setPosts(posts);
			userService.updateUserByUsername(postSaved.getUsername(), user);
		}
	}

	@Override
	public List<Post> getAllPosts() {
		List<Post> posts =  blogRepo.findAll();
		
		if (posts.size() > 0) {
			return posts;
		} else {
			return new ArrayList<Post>();
		}
	}

	@Override
	public Post getSinglePost(String id) throws BlogCollectionException {
		Optional<Post> optionalPost =  blogRepo.findById(id);
		if (!optionalPost.isPresent()) {
			throw new BlogCollectionException(BlogCollectionException.PostNotFoundException(id));
		} else {
			return optionalPost.get();
		}
	}

	@Override
	public void updatePost(String id, Post post) throws BlogCollectionException, UserCollectionException {
		Optional<Post> postWithId = blogRepo.findById(id);
		if (postWithId.isPresent()) {
			Post postToUpdate = postWithId.get();
			postToUpdate.setTitle(post.getTitle());
			postToUpdate.setAuthor(post.getAuthor());
			postToUpdate.setTags(post.getTags());
			postToUpdate.setCategory(post.getCategory());
			postToUpdate.setDescription(post.getDescription());
			postToUpdate.setContent(post.getContent());
			postToUpdate.setUpdatedAt(LocalDateTime.now());
			Post postSaved = blogRepo.save(postToUpdate);
			
			User user = userService.getUserByUsername(postSaved.getUsername());
			List<Post> posts = user.getPosts();
			if (posts == null) {
				posts = new ArrayList<Post>();
			} else {
				int index = IntStream.range(0, user.getPosts().size())
					     .filter(i -> user.getPosts().get(i).getId().equals(id))
					     .findFirst()
					     .orElse(-1);
				posts.set(index, postSaved);
				user.setPosts(posts);
				userService.updateUserByUsername(postSaved.getUsername(), user);
			}	
			
		} else {
			throw new BlogCollectionException(BlogCollectionException.PostNotFoundException(id));
		}
		
	}

	@Override
	public void deletePostById(String id) throws BlogCollectionException, UserCollectionException {
		Optional<Post> postOptional = blogRepo.findById(id);
		if (!postOptional.isPresent()) {
			throw new BlogCollectionException(BlogCollectionException.PostNotFoundException(id));
		} else {
			blogRepo.deleteById(id);
			Post post = postOptional.get();
			User user = userService.getUserByUsername(post.getUsername());
			List<Post> posts = user.getPosts();
			if (posts == null) {
				posts = new ArrayList<Post>();
			} else {
				int index = IntStream.range(0, user.getPosts().size())
					     .filter(i -> user.getPosts().get(i).getId().equals(id))
					     .findFirst()
					     .orElse(-1);
				posts.remove(index);
				user.setPosts(posts);
				userService.updateUserByUsername(post.getUsername(), user);
			}
		}
	}
	
}
