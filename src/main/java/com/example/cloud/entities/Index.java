package com.example.cloud.entities;

public class Index {
	
	String word;
	
	Long occurence;

	public Index() {
		super();
	}

	public Index(String word, Long occurence) {
		super();
		this.word = word;
		this.occurence = occurence;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getOccurence() {
		return occurence;
	}

	public void setOccurence(Long occurence) {
		this.occurence = occurence;
	}

}
