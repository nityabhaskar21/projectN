package com.projectN.app.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="users")
public class User {
	@Id
	private String id;
	
	@NotNull(message= "Username cannot be null")
	private String username;
	
	@NotNull(message= "Password cannot be null")
	private String password;
	
	@NotNull(message= "Firstname cannot be null")
	private String firstname;
	
	@NotNull(message= "Lastname cannot be null")
	private String lastname;
	
	@NotNull(message= "Email cannot be null")
	private String email;
	
	private List<Post> posts;
	
	private String about;
	
	@NotNull(message= "Role cannot be null")
	private Role role;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;

	public User(@NotNull(message = "Username cannot be null") String username,
			@NotNull(message = "Password cannot be null") String password,
			@NotNull(message = "Email cannot be null") String email, 
			String about,
			@NotNull(message = "Role cannot be null") Role role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.about = about;
		this.role = role;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
