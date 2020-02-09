package com.example.cloud.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.cloud.entities.Closeness;
import com.example.cloud.entities.Graphe;

public class UtilCloseness {
	
	private static List<Graphe> graphe = null;
	
	public static double computeCloseness(String file) {
		assert graphe == null : "You must call computeClosenessFiles and calculShortestPathsAndDistance first";
		double n= graphe.stream().map(p -> p.getBook1()).distinct().count()-1;
		double sum = graphe.stream().filter(p -> p.getBook1().equals(file) || p.getBook2().equals(file)).filter(p-> p.getShortDistance()>0)
				.mapToDouble(p-> p.getShortDistance()).sum();
		if (n/sum <0) {
			return -1.0;
		}
		return n/sum;
	}
	
	public static List<Closeness> computeClosenessFiles(List<Graphe> graphe){
		UtilCloseness.graphe = graphe;
		List<Closeness> closenesses = new ArrayList<>();
		graphe.stream().map(p->p.getBook1()).distinct().forEach(node -> {
				Closeness closeness = new Closeness(node, computeCloseness(node));
				closenesses.add(closeness);
			});
		return closenesses.stream().filter(c -> c.getCloseness()>0).collect(Collectors.toList());
	}

}
