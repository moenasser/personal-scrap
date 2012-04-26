package com.mnasser.graph;



public class DirectedGraph extends AdjacencyListGraph{

	//private boolean reversed = false;
	private Vertex max = null;
	private Vertex min = null;
	
	@Override public boolean isDirected() { return true;}
	
	@Override
	public void addEdge(Edge e) {
		// no parallel edges 
		if( ! hasEdge(e) )
			super.addEdge(e);
	}
	
	@Override
	public Vertex addVertex(Vertex v) {
		Vertex v1 = super.addVertex(v);
		max = (max == null)?v : (max.id < v.id)? v : max;
		min = (min == null)?v : (min.id > v.id)? v : min;
		return v1;
	}
	
	Vertex getMax(){ return this.max; }
	Vertex getMin(){ return this.min; }
	
	public void clearVisited(){
		for( Vertex a : getVertices() ){
			a.visited = false;
		}
	}
	
	public void clearOrdering(){
		for( Vertex a : getVertices() ){
			a.order = -1;
		}
	}

	/*
	void reverse(){
		this.reversed = true;
	}
	void unReverse(){
		this.reversed = false;
	}
	void setReverse(boolean rev){
		this.reversed = rev;
	}
	boolean isRevered(){ return this.reversed; }
	*/
	
	/*
	DirectedGraph reverseClone(){
		DirectedGraph g = (DirectedGraph) Graph.copyOf(this);
		g.reversed = ! this.reversed;
		return g;
	}
	*/
}
