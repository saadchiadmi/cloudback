package com.example.cloud.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "closeness")
public class Closeness {
	
	@Id
	String book;
	
	double closeness;

	public Closeness() {
		super();
	}

	public Closeness(String book, double closeness) {
		super();
		this.book = book;
		this.closeness = closeness;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public double getCloseness() {
		return closeness;
	}

	public void setCloseness(double closeness) {
		this.closeness = closeness;
	}
	
	@Override
	public String toString() {
		return this.book+" "+closeness;
	}

}
