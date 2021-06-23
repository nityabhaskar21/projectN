package com.projectN.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectN.app.exception.BlogCollectionException;
import com.projectN.app.model.Post;
import com.projectN.app.repo.BlogRepository;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private BlogRepository blogRepo;

	@Override
	public void createPost(Post post) throws ConstraintViolationException, BlogCollectionException {
		Optional<Post> postOptional =  blogRepo.findPostByTitle(post.getTitle());
		
		if (postOptional.isPresent()) {
			throw new BlogCollectionException(BlogCollectionException.PostAlreadyExists());
		} else {
			post.setCreatedAt(LocalDateTime.now());
			post.setUpdatedAt(LocalDateTime.now());
			blogRepo.save(post);
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
			throw new BlogCollectionException(BlogCollectionException.NotFoundException(id));
		} else {
			return optionalPost.get();
		}
	}

	@Override
	public void updatePost(String id, Post post) throws BlogCollectionException {
		Optional<Post> postWithId = blogRepo.findById(id);
		if (postWithId.isPresent()) {
			Post postToUpdate = postWithId.get();
			postToUpdate.setTitle(post.getTitle());
			postToUpdate.setAuthor(post.getAuthor());
			postToUpdate.setTags(post.getTags());
			postToUpdate.setContent(post.getContent());
			postToUpdate.setUpdatedAt(LocalDateTime.now());
			
			blogRepo.save(postToUpdate);
			
		} else {
			throw new BlogCollectionException(BlogCollectionException.NotFoundException(id));
		}
		
	}

	@Override
	public void deletePostById(String id) throws BlogCollectionException {
		Optional<Post> postOptional = blogRepo.findById(id);
		if (!postOptional.isPresent()) {
			throw new BlogCollectionException(BlogCollectionException.NotFoundException(id));
		} else {
			blogRepo.deleteById(id);
		}
	}
	
}
