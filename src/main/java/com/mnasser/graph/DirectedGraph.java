package com.mnasser.graph;


/**
 * A directed graph. 
 * </p>
 * This means that all edges have a direction from a source vertex to a 
 * destination vertex.  Being direct it means that one can only traverse edges
 * on this graph in one direction : from outbound to inbound. 
 * </p>
 * To make a bi-directional pair of vertices (from which you can traverse back
 * and forth between them) you will need 2 edges : one for each direction. 
 * 
 * @author Moe
 *
 */
public class DirectedGraph extends AdjacencyListGraph{

	@Override public boolean isDirected() { return true;}
	
	//private boolean reversed = false;
	private Vertex max = null;
	private Vertex min = null;
	

	public DirectedGraph() {
		super();
	}
	public DirectedGraph(int i) {
		super(i);
	}

	
	@Override
	public void addEdge(Edge e) {
		// no parallel edges 
		if( ! hasEdge(e) )
			super.addEdge(e);
	}
	
	@Override
	public Vertex addVertex(Vertex v) {
		Vertex v1 = super.addVertex(v);
		max = (max == null)? v : (max.id < v.id)? v : max;
		min = (min == null)? v : (min.id > v.id)? v : min;
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
