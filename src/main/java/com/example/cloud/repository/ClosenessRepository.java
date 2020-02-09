package com.example.cloud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.cloud.entities.Closeness;

public interface ClosenessRepository extends MongoRepository<Closeness, String>{

}
