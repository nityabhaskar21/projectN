package com.projectN.app.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.projectN.app.model.Post;

@Repository
public interface BlogRepository extends MongoRepository<Post, String>{

	@Query("{'title':?0}")
	Optional<Post> findPostByTitle(String post);
}
