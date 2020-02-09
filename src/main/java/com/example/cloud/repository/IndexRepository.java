package com.example.cloud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.cloud.entities.Index;

public interface IndexRepository extends MongoRepository<Index, String> {

}
