package com.mnasser.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple implementation of a Graph as listing of all edges <code>E</code>
 * between vertices <code>V</code>.
 * 
 * </p>
 * TODO : replace array of vertices/edges with a Heap or Binary tree of sorts
 * @author Moe
 */
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
	private int connected_vertices = 0;

	@Override public List<Edge> getEdges() {return edges;}
	@Override public List<Vertex> getVertices() { return vertices; } // whyyy sort!?? Sooo slow!! //Collections.sort(vertices); return vertices; }
	
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
			// clone it
			//Vertex v1 = new Vertex(v.id); 
			//v1.directed = isDirected();
			vertices.add(v);
			_vertmap.put(v.id, v);
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
	public synchronized void addEdge(int ia, int ib){
		Vertex a = addVertex(ia);
		Vertex b = addVertex(ib);
		Edge e1 = new Edge( a, b );
		a.edges.add(e1);
		b.edges.add(e1);
		edges.add( e1 );
		
		if( a.edges.size() == 1 ) connected_vertices++;
		if( b.edges.size() == 1 ) connected_vertices++;
	}
	public synchronized void addEdge(Edge e){
		addVertex(e.src);
		addVertex(e.dst);
		Vertex a = getVertex(e.src); 
		Vertex b = getVertex(e.dst);
		Edge e1 = new Edge( a, b , e.cost());
		a.edges.add(e1);
		b.edges.add(e1);
		edges.add( e1 );
		
		if( a.edges.size() == 1 ) connected_vertices++;
		if( b.edges.size() == 1 ) connected_vertices++;
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
		//return (hasVertex(id))? _vertmap.get(id) : null;
		return _vertmap.get(id);
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
	

	/**Returns true iff there exists at least 1 vertex with
	 * no edges leading to or from it.*/
	public boolean hasDisjointNodes(){
		return connected_vertices < vertices.size();
		//	for( Vertex v : vertices){
		//		if ( v.edges.isEmpty() )
		//			return false;
		//	}
		//	return true;
	}

	/**Removes the given edge from this graph.  
	 * </p>
	 * The vertices incident on this edge will also no longer have this edge 
	 * connecting them */
	public void removeEdge(Edge e){
		if(hasEdge(e)){
			e.src.removeEdge(e);
			e.dst.removeEdge(e);
			edges.remove(e);
		}
	}
	
	/**Removes the vertex from this graph.
	 * </p>
	 * After this operation all other vertices will have edges incident on 
	 * <code>v</code> removed as well. ie, all edges touching <code>v</code> 
	 * will also be removed */
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
	
}
