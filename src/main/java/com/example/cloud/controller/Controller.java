package com.example.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloud.entities.Closeness;
import com.example.cloud.entities.TimeExecution;
import com.example.cloud.repository.BookRepository;
import com.example.cloud.repository.ClosenessRepository;
import com.example.cloud.repository.GrapheRepository;
import com.example.cloud.util.UtilIndex;
import com.example.cloud.util.UtilCloseness;
import com.example.cloud.util.UtilGraphe;


@RestController
@CrossOrigin(origins="http://localhost:4200")
public class Controller {

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	GrapheRepository grapheRepository;
	
	@Autowired
	ClosenessRepository closenessRepository;
	
	static String Docs = "docs/";
	
	@GetMapping("/start")
	public TimeExecution getTimeExecution() {
		TimeExecution timeExecution = new TimeExecution();
		//bookRepository.deleteAll();
		grapheRepository.deleteAll();
		closenessRepository.deleteAll();
		
		long start = System.currentTimeMillis();
		UtilIndex.createFileIndexOfDirectory(Docs, bookRepository);
		timeExecution.setIndex(System.currentTimeMillis() - start);
		
		System.out.println("Finish Indexing "+timeExecution.getIndex());
		
		start = System.currentTimeMillis();
		UtilGraphe.computeJaccard(bookRepository.findAll(), grapheRepository);
		timeExecution.setGraphe(System.currentTimeMillis() - start);
		
		System.out.println("Finish Graphe building "+timeExecution.getGraphe());
		
		start = System.currentTimeMillis();
		List<Closeness> closenesses = UtilCloseness.computeClosenessFiles(grapheRepository.findAll());
		closenessRepository.saveAll(closenesses);
		timeExecution.setCloseness(System.currentTimeMillis() - start);
		return timeExecution;
	}
	
}
