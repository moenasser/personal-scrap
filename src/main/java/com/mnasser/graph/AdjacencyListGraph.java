package com.mnasser.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph extends Graph{
	
	private List<Vertex> vertices = new ArrayList<Vertex>();
	private List<Edge>   edges    = new ArrayList<Edge>();
	private Map<Integer,Vertex> _vertmap = new HashMap<Integer,Vertex>();

	@Override public List<Edge> getEdges() {return edges;}
	@Override public List<Vertex> getVertices() { return vertices; }
	
	
	void addVertex(){
		int id = makeAnId();
		addVertex(id);
	}
	void addVertex(int id){
		if( ! _vertmap.containsKey(id) ){
			Vertex v = new Vertex(id);
			vertices.add(v);
			_vertmap.put(id, v);
		}
	}
	public void addVertex(Vertex v){
		if( _vertmap.containsKey(v.id) ){
			Vertex v1 = _vertmap.get(v.id);
			if( v1 != v )
				v1.edges.addAll(v.edges);
		}else{
			vertices.add(v);
			_vertmap.put(v.id, v);
		}
	}
	public void addEdge(Vertex a, Vertex b){
		Edge e = new Edge(a,b);
		a.edges.add(e);
		b.edges.add(e);
		edges.add(e);
	}
	public void addEdge(Edge e){
		addVertex(e.head);
		addVertex(e.tail);
		e.head.edges.add(e);
		e.tail.edges.add(e);
		edges.add(e);
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
	

	
	public boolean isConnected(){
		for( Vertex v : vertices){
			if ( v.edges.isEmpty() )
				return false;
		}
		return true;
	}

	public void removeEdge(Edge e){
		if(hasEdge(e)){
			e.head.removeEdge(e);
			e.tail.removeEdge(e);
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
		for( int ii = 0;  ! G.isConnected();  ){
			//int a = ( (int)(Math.random()*vertexSize) % vertexSize );
			//int b = ( (int)(Math.random()*vertexSize) % vertexSize) + 1;
			Vertex a = G.getVertices().get( ((int)(Math.random()*vertexSize) % vertexSize) );
			Vertex b = G.getVertices().get( ((int)(Math.random()*vertexSize) % vertexSize) );
			if( a != b  && ! G.hasEdge( a, b )  ){
				G.addEdge(  new Edge( a, b )  );
				ii ++;
			}
		}
		return G;
	}
	
}
