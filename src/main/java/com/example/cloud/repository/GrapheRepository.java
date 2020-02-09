package com.example.cloud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.cloud.entities.Graphe;

public interface GrapheRepository extends MongoRepository<Graphe, Integer>{

}
