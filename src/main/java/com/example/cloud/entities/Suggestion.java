package com.example.cloud.entities;

public class Suggestion {
	
	String book1;
	
	String book2;
	
	double closeness;

	public Suggestion(String book1, String book2, double closeness) {
		this.book1 = book1;
		this.book2 = book2;
		this.closeness = closeness;
	}

	public String getBook1() {
		return book1;
	}

	public void setBook1(String book1) {
		this.book1 = book1;
	}

	public String getBook2() {
		return book2;
	}

	public void setBook2(String book2) {
		this.book2 = book2;
	}

	public double getCloseness() {
		return closeness;
	}

	public void setCloseness(double closeness) {
		this.closeness = closeness;
	}

}
