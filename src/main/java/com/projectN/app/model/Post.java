package com.projectN.app.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="posts")
public class Post {
	@Id
	private String id;
	
	@NotNull(message= "Title cannot be null")
	private String title;
	
	@NotNull(message= "Description cannot be null")
	private String description;
	
	@NotNull(message= "Category cannot be null")
	private String category;
	
	@NotNull(message= "Tags cannot be null")
	private List<String> tags;
	
	@NotNull(message= "Author cannot be null")
	private String author;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@NotNull(message= "Content cannot be null")
	private String content;
	
	public Post( String title, List<String> tags, String author, String content) {
		super();
		this.title = title;
		this.tags = tags;
		this.author = author;
		this.content = content;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	
}
