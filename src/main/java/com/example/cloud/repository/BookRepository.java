package com.example.cloud.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.cloud.entities.Book;

public interface BookRepository extends MongoRepository<Book, String>{

	@Query(value = "{'index.word': {$eq :?0}}", fields = "{'name' : 1, 'index' : {$elemMatch : { 'word' : ?0 }}}")
	public List<Book> findByIndex(String word);
	
	@Query(value = "{'index.word': {'$regex': ?0}}", fields = "{'name' : 1, 'index' : {$elemMatch : { 'word' : {'$regex': ?0} }}}")
	public List<Book> findRegexByIndex(String word);
	
}
