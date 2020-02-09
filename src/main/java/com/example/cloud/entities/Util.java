package com.example.cloud.entities;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Util {
	
	String name;
	double occ;
	public Util(String name, double occ) {
		super();
		this.name = name;
		this.occ = occ;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getOcc() {
		return occ;
	}
	public void setOcc(double occ) {
		this.occ = occ;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
	public static boolean isRegex(final String str) {
	    try {
	        Pattern.compile(str);
	        return true;
	    } catch (java.util.regex.PatternSyntaxException e) {
	        return false;
	    }
	}

}
