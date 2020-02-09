package com.example.cloud.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book")
public class Book {
	
	@Id
	private String name;
	
	public List<Index> index;

	public Book() {
		super();
	}

	public Book(String name) {
		super();
		this.name = name;
		this.index = new ArrayList<Index>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Index> getIndex() {
		return index;
	}

	public void setIndex(List<Index> index) {
		this.index = index;
	}
	
}
