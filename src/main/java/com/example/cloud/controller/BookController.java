package com.example.cloud.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloud.entities.Book;
import com.example.cloud.entities.Closeness;
import com.example.cloud.entities.Graphe;
import com.example.cloud.entities.Suggestion;
import com.example.cloud.entities.Util;
import com.example.cloud.repository.BookRepository;
import com.example.cloud.repository.ClosenessRepository;
import com.example.cloud.repository.GrapheRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	ClosenessRepository closenessRepository;
	
	@Autowired
	GrapheRepository grapheRepository;
	
	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return bookRepository.findAll().stream().map(b->new Book(b.getName())).collect(Collectors.toList());
	}
	
	@GetMapping("/books/closeness")
	public List<Book> getAllBooksByCloseness() {
		List<Closeness> closenesses = closenessRepository.findAll();
		return bookRepository.findAll().stream().map(b-> new Util(b.getName(), closenesses.stream().filter(c -> c.getBook().equals(b.getName())).map(c->c.getCloseness()).findFirst().orElse(-1.0)))
				.filter(u -> u.getOcc()>0)
				.sorted(Comparator.comparingDouble(Util::getOcc).reversed())
				.map(u -> new Book(u.getName())).collect(Collectors.toList());
	}
	
	@GetMapping("/books/{name}")
	public List<Book> getBooksByName(@PathVariable String name) {
		return bookRepository.findByIndex(name).stream().map(b-> new Util(b.getName(), b.getIndex().get(0).getOccurence())).sorted(Comparator.comparingDouble(Util::getOcc).reversed()).map(u -> new Book(u.getName())).collect(Collectors.toList());
	}
	
	@GetMapping("/books/{name}/closeness")
	public List<Book> getBooksClosenessByName(@PathVariable String name) {
		List<Closeness> closenesses = closenessRepository.findAll();
		return bookRepository.findByIndex(name).stream().map(b-> new Util(b.getName(), closenesses.stream().filter(c -> c.getBook().equals(b.getName())).map(c->c.getCloseness()).findFirst().orElse(-1.0)))
				.filter(u -> u.getOcc()>0)
				.sorted(Comparator.comparingDouble(Util::getOcc).reversed())
				.map(u -> new Book(u.getName())).collect(Collectors.toList());
	}
	
	@GetMapping("/books/{name}/regex/closeness")
	public List<Book> getBooksClosenessRegexByName(@PathVariable String name) {
		List<Closeness> closenesses = closenessRepository.findAll();
		return bookRepository.findRegexByIndex(name).stream().map(b-> new Util(b.getName(), closenesses.stream().filter(c -> c.getBook().equals(b.getName())).map(c->c.getCloseness()).findFirst().orElse(-1.0)))
				.filter(u -> u.getOcc()>0)
				.sorted(Comparator.comparingDouble(Util::getOcc).reversed())
				.map(u -> new Book(u.getName())).collect(Collectors.toList());
	}
	
	@GetMapping("/books/{name}/regex")
	public List<Book> getBooksRegexByName(@PathVariable String name) {
		if(Util.isRegex(name)) {
			return bookRepository.findRegexByIndex(name).stream().map(b-> new Util(b.getName(), b.getIndex().get(0).getOccurence())).sorted(Comparator.comparingDouble(Util::getOcc).reversed()).map(u -> new Book(u.getName())).collect(Collectors.toList());
		}else {
			return null;
		}
	}
	
	@GetMapping("/books/{name}/suggestion")
	public List<Book> getBooksSuggestionByName(@PathVariable String name) {
		List<Closeness> closenesses = closenessRepository.findAll();
		List<Graphe> graphe = grapheRepository.findAll();
		return this.getBooksByName(name).stream().limit(3).map(b ->{
					List<Suggestion> suggestions = new ArrayList<>();
					closenesses.forEach(c -> suggestions.add(new Suggestion(b.getName(), c.getBook(), c.getCloseness())));
					return suggestions;
				}).flatMap(suggestions -> suggestions.stream().filter(s -> Graphe.checkIsNeighbours(s.getBook1(), s.getBook2(), graphe))
															.sorted(Comparator.comparingDouble(Suggestion::getCloseness)))
					.filter(Util.distinctByKey(Suggestion::getBook2))
					.map(b-> new Util(b.getBook2(), b.getCloseness()))
					.map(u -> new Book(u.getName())).collect(Collectors.toList());

	}
	
	@GetMapping("/books/{name}/regex/suggestion")
	public List<Book> getBooksRegexSuggestionByName(@PathVariable String name) {
		if(Util.isRegex(name)) {
			List<Closeness> closenesses = closenessRepository.findAll();
			List<Graphe> graphe = grapheRepository.findAll();
			return this.getBooksRegexByName(name).stream().limit(3).map(b ->{
						List<Suggestion> suggestions = new ArrayList<>();
						closenesses.forEach(c -> suggestions.add(new Suggestion(b.getName(), c.getBook(), c.getCloseness())));
						return suggestions;
					}).flatMap(suggestions -> suggestions.stream().filter(s -> Graphe.checkIsNeighbours(s.getBook1(), s.getBook2(), graphe))
													.sorted(Comparator.comparingDouble(Suggestion::getCloseness)))
						.filter(Util.distinctByKey(Suggestion::getBook2))
						.map(b-> new Util(b.getBook2(), b.getCloseness()))
						.map(u -> new Book(u.getName())).collect(Collectors.toList());
		}
		else return null;
	}
	
	@PostMapping("/books")
	public Book createBook(@RequestBody Book book) {
		return bookRepository.save(book);
	}
	
	@DeleteMapping("/books/{name}")
	public Book deleteBook(@PathVariable String name) {
		Book result = getBooksByName(name).get(0);
		bookRepository.deleteById(name);
		return result;
	}

}
