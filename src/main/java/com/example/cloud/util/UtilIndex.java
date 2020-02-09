package com.example.cloud.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import com.example.cloud.entities.Book;
import com.example.cloud.entities.Index;
import com.example.cloud.repository.BookRepository;

public class UtilIndex {
	
	private static BookRepository bookRepository;
	
	private static Map<String,Long> createMapIndex(String path){
		try (Stream<String> lines = Files.lines(Paths.get("docs/"+path))) {
			return lines.flatMap(l -> Arrays.stream(l.split("[^A-Za-z]+")))
		     .map(w->w.toLowerCase())
		     .filter(w-> w.length()>3)
		     .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static void createFileIndex(String path) {
		Book book = new Book(path);
		createMapIndex(path).entrySet().stream()
	     .forEach(entry -> {
	    	 Index index = new Index(entry.getKey(), entry.getValue());
	    	 book.index.add(index);
	    	 
	     });
		bookRepository.save(book);
	}
	
	public static void createFileIndexOfDirectory(String directory, BookRepository bookRepository) {
		UtilIndex.bookRepository = bookRepository;
		try(Stream<Path> files = Files.walk(Paths.get(directory))){
			files.filter(Files::isRegularFile)
			.map(f -> f.getFileName().toString())
			.forEach(f -> createFileIndex(f));
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
