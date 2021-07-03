package com.projectN.app.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.projectN.app.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	@Query("{'username':?0}")
	Optional<User> findUserByUsername(String username);

}
