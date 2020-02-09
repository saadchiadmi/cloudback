package com.example.cloud.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "graphe")
public class Graphe {
	
	String book1;
	
	String book2;
	
	double distance;
	
	double shortDistance;
	
	boolean neighbour;

	public Graphe() {
		super();
	}

	public Graphe(String book1, String book2, double distance, double shortDistance) {
		super();
		this.book1 = book1;
		this.book2 = book2;
		this.distance = distance == Double.POSITIVE_INFINITY ? -1.0 : distance;
		this.shortDistance =shortDistance == Double.POSITIVE_INFINITY ? -1.0 : shortDistance;
		this.neighbour = (this.distance != -1.0) && (distance == shortDistance) ? true : false;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getShortDistance() {
		return shortDistance;
	}

	public void setShortDistance(double shortDistance) {
		this.shortDistance = shortDistance;
	}

	public boolean isNeighbour() {
		return neighbour;
	}

	public void setNeighbour(boolean neighbour) {
		this.neighbour = neighbour;
	}
	
	public static boolean checkIsNeighbours(String book1, String book2, List<Graphe> graphe) {
		return graphe.stream().filter(node -> (node.getBook1().equals(book1) && node.getBook2().equals(book2)) || (node.getBook1().equals(book2) && node.getBook2().equals(book1)))
				.map(node -> node.isNeighbour()).findFirst().orElse(false);
	}

	@Override
	public String toString() {
		return book1+" "+book2+" "+distance+" "+shortDistance+" "+neighbour;
	}

}
