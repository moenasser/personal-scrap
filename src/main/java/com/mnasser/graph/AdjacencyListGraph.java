package com.mnasser.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph extends Graph{
	
	public AdjacencyListGraph(int initialSize) {
		vertices = new ArrayList<Vertex>(initialSize);	
		edges = new ArrayList<Edge>(initialSize);
		_vertmap = new HashMap<Integer,Vertex>(initialSize);
	}
	public AdjacencyListGraph() {
		this(100);
	}
	
	private List<Vertex> vertices = null; 
	private List<Edge>   edges    = null; 
	private Map<Integer,Vertex> _vertmap = null;

	@Override public List<Edge> getEdges() {return edges;}
	@Override public List<Vertex> getVertices() { Collections.sort(vertices); return vertices; }
	
	public int getEdgeCount(){		return edges.size();	}
	public int getVertexCount(){	return vertices.size(); }
	
	Vertex addVertex(){
		int id = makeAnId();
		return addVertex(id);
	}
	Vertex addVertex(int id){
		if( ! _vertmap.containsKey(id) ){
			Vertex v = new Vertex(id);
			return addVertex(v);
		}
		return _vertmap.get(id);
	}
	public Vertex addVertex(Vertex v){
		if( ! _vertmap.containsKey(v.id) ){
			Vertex v1 = new Vertex(v.id); // clone it
			v1.directed = isDirected();
			vertices.add(v1);
			_vertmap.put(v1.id, v1);
		}
		return _vertmap.get(v.id);
	}
	public void addEdge(Vertex a, Vertex b){
		Edge e = new Edge(a,b);
		addEdge(e);
		/*
		a.edges.add(e);
		b.edges.add(e);
		edges.add(e);
		*/
	}
	public void addEdge(int ia, int ib){
		Vertex a = addVertex(ia);
		Vertex b = addVertex(ib);
		Edge e1 = new Edge( a, b );
		a.edges.add(e1);
		b.edges.add(e1);
		edges.add( e1 );
	}
	public void addEdge(Edge e){
		addVertex(e.src);
		addVertex(e.dst);
		Vertex a = getVertex(e.src); 
		Vertex b = getVertex(e.dst);
		Edge e1 = new Edge( a, b );
		a.edges.add(e1);
		b.edges.add(e1);
		edges.add( e1 );
	}
	public boolean hasEdge(Edge e){
		return  edges.contains(e);
	}
	public boolean hasEdge(Vertex a, Vertex b){
		return ( hasVertex(a) && hasVertex(b) && getVertex(a).hasNeighbor(b));
	}
	public boolean hasEdge(int a, int b){
		return (_vertmap.containsKey(a) && _vertmap.containsKey(b)
			&& _vertmap.get(a).hasNeighbor(_vertmap.get(b)));
	}
	public boolean hasVertex(int id){
		return _vertmap.containsKey(id);
	}
	public boolean hasVertex(Vertex a){
		return (a==null)? false : _vertmap.containsKey(a.id);
	}
	public Vertex getVertex(int id){
		return (hasVertex(id))? _vertmap.get(id) : null;
	}
	public Vertex getVertex(Vertex a){
		return (a==null)? null : getVertex(a.id);
	}
	public Edge getEdge(Vertex a, Vertex b){
		Vertex A = getVertex(a);
		return A.getEdge(b);
	}
	@Override
	public Edge getEdge(int a, int b) {
		Vertex A = getVertex(a);
		Vertex B = getVertex(b);
		if ( A != null && B != null){
			return A.getEdge(B);
		}
		return null;
	}
	

	
	public boolean hasDisjointNodes(){
		for( Vertex v : vertices){
			if ( v.edges.isEmpty() )
				return false;
		}
		return true;
	}

	public void removeEdge(Edge e){
		if(hasEdge(e)){
			e.src.removeEdge(e);
			e.dst.removeEdge(e);
			edges.remove(e);
		}
	}
	
	public void removeVertex(Vertex v){
		// find the edges in v and remove them
		// then remove v
		List<Edge> toRemove = new ArrayList<Edge>();
		toRemove.addAll(v.edges);
		
		for(Edge e : toRemove){
			removeEdge(e);
		}
		
		vertices.remove(v);
		_vertmap.remove(v.id);
	}
	
	public static AdjacencyListGraph makeRandomGraph(int vertexSize){
		AdjacencyListGraph G = new AdjacencyListGraph();
		for( int ii =0; ii< vertexSize ; ii++){
			G.addVertex();
		}
		while ( ! G.hasDisjointNodes() ){
			Vertex a = G.getVertices().get( ((int)(Math.random()*vertexSize) % vertexSize) );
			Vertex b = G.getVertices().get( ((int)(Math.random()*vertexSize) % vertexSize) );
			if( a != b  && ! G.hasEdge( a, b )  ){
				G.addEdge(  new Edge( a, b )  );
			}
		}
		return G;
	}
	
}
