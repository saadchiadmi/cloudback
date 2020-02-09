package com.example.cloud.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.cloud.entities.Book;
import com.example.cloud.entities.Graphe;
import com.example.cloud.entities.Jaccard;
import com.example.cloud.repository.GrapheRepository;

public class UtilGraphe {
	
	private static GrapheRepository grapheRepository;
	private static Map<String,Long> map1;
	private static Map<String,Long> map2;
	private static List<Jaccard> jaccard;
	private static List<Book> allBooks;
	private static double[][] dist = null;
	private static double edgeThreshold = 0.6;
	private static int a=0;
	
	public static void computeJaccard(List<Book> books, GrapheRepository grapheRepository) {
		allBooks = books;
		UtilGraphe.grapheRepository = grapheRepository;
		jaccard = new ArrayList<Jaccard>();
		System.out.println("start computing jaccard");
		a=0;
		books.stream()
			.forEach(f -> {
				System.out.print("book "+add()+" ");
				computeJaccardBook(f);
				System.out.println();
			});
		System.out.println("start Warshall");
		calculShortestPathsAndDistance();
	}
	
	public static String add() {
		a++;
		return ""+a;
	}
	
	private static void computeJaccardBook(Book book){
		allBooks.stream()
		.filter(f2 -> f2.getName().compareTo(book.getName()) > 0)
		.forEach(f2 -> distanceJaccard(book, f2));
	}
	
	private static void distanceJaccard(Book f1, Book f2) {
		map1 = allBooks.stream().filter(b -> b.getName().equals(f1.getName())).flatMap(b -> b.getIndex().stream())
				.collect(Collectors.toMap(a -> a.getWord(), a -> a.getOccurence()));
		map2 = allBooks.stream().filter(b -> b.getName().equals(f2.getName())).flatMap(b -> b.getIndex().stream())
				.collect(Collectors.toMap(a -> a.getWord(), a -> a.getOccurence()));
		map1.keySet().retainAll(map2.keySet());
		map2.keySet().retainAll(map1.keySet());
		double distance = computeJaccardMap(map1, map2);
		if(distance <= edgeThreshold) {
			Jaccard res = new Jaccard(f1.getName(), f2.getName(), distance);
			jaccard.add(res);
		}
	}
	
	private static double computeJaccardMap(Map<String,Long> m1, Map<String,Long> m2) {
		Long num = m1.entrySet().stream()
				.mapToLong(e -> Math.abs(e.getValue()-m2.get(e.getKey()))).sum();
		Long den = m2.entrySet().stream()
				.mapToLong(e -> Math.max(e.getValue(), m1.get(e.getKey()))).sum();
		
		if(den > 0) {
			return  num*1.0/den;
		}
		else {
			return 0.0;
		}
	}
	
	public static void calculShortestPathsAndDistance() {
		List<String> books = jaccard.stream().map(p->p.getBook1()).distinct().collect(Collectors.toList());
		dist = new double[books.size()][books.size()];
		System.out.println("inizialize dist");
	    for (int i=0;i<dist.length;i++) {
	    	System.out.println("book "+i);
	      for (int j=0;j<dist.length;j++) {
	        if (i==j) {dist[i][i]=0; continue;}
        	dist[i][j]=distance(books.get(i),books.get(j));
	      }
	    }
	    
	    for (int k=0;k<dist.length;k++) {
	    	System.out.println("book "+k);
	      for (int i=0;i<dist.length;i++) {
	        for (int j=0;j<dist.length;j++) {
	          if (dist[i][j]>dist[i][k] + dist[k][j]){
	            dist[i][j]=dist[i][k] + dist[k][j];
	          }
	        }
	      }
	    }
	    System.out.println("start set");
	    setNeighboursAndShorDictanse();
	}
	
	public static double distance(String f1, String f2) {
		return jaccard.stream().filter(p -> (p.getBook1().equals(f1) && p.getBook2().equals(f2)) || (p.getBook1().equals(f2) && p.getBook2().equals(f1)))
				.map(p -> p.getDistance()).findFirst().orElse(Double.POSITIVE_INFINITY);
	}
	
	private static void setNeighboursAndShorDictanse() {
		assert dist == null : "You must call computeJaccard and calculShortestPathsAndDistance first";
		List<String> books = jaccard.stream().map(p->p.getBook1()).distinct().collect(Collectors.toList());
		for (int i = 0; i < dist.length; i++) {
			System.out.println("book "+i);
			for (int j = i+1; j < dist.length; j++) {
				Graphe res = new Graphe(books.get(i), books.get(j), distance(books.get(i),books.get(j)), dist[i][j]);
				grapheRepository.save(res);
			}
		}
	}
	
	
}
