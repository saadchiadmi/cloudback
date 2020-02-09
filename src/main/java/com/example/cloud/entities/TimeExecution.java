package com.example.cloud.entities;

public class TimeExecution {
	
	long index;
	
	long graphe;
	
	long closeness;

	public TimeExecution() {
		this.index = 0;
		this.graphe = 0;
		this.closeness = 0;
	}

	public TimeExecution(long index, long graphe, long warshall, long closeness) {
		this.index = index;
		this.graphe = graphe;
		this.closeness = closeness;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public long getGraphe() {
		return graphe;
	}

	public void setGraphe(long graphe) {
		this.graphe = graphe;
	}

	public long getCloseness() {
		return closeness;
	}

	public void setCloseness(long closeness) {
		this.closeness = closeness;
	}

}
