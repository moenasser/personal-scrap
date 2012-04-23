package com.mnasser.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdjacencyListGraph {
	
	private Map<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	private List<Edge>   edges    = new ArrayList<Edge>();

	public static class Vertex {
		public final int id;
		public Set<Edge> edges;
		public Vertex(int id) {
			this.id = id;
			this.edges = new HashSet<Edge>();
		}
		boolean hasNeighbor(Vertex b){
			for(Edge e : edges){
				if( b.equals( e.otherSide(this) ))
					return true;
			}
			return false;
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(id).append(" -> [");
			for( Edge e : edges ){
				sb.append(e.otherSide(this).id)
				  .append(',');
			}
			if( edges.size()>0 ) sb.deleteCharAt(sb.length()-1);
			sb.append(']');
			return sb.toString();
		}
		@Override
		public int hashCode() {
			return id;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (id != other.id)
				return false;
			return true;
		}
	}
	
	public static class Edge   {
		private final Vertex head, tail;
		public Edge(Vertex a, Vertex b) {
			this.head = a;
			this.tail = b;
		}
		/**Vertices use this to get what's on the other side of the edge*/
		public Vertex otherSide(Vertex head){
			if( this.head == head )
				return this.tail;
			if( this.tail == head )
				return this.head;
			return null;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((head == null) ? 0 : head.hashCode());
			result = prime * result + ((tail == null) ? 0 : tail.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge other = (Edge) obj;
			if (head == null) {
				if (other.head != null)
					return false;
			} else if (!head.equals(other.head))
				return false;
			if (tail == null) {
				if (other.tail != null)
					return false;
			} else if (!tail.equals(other.tail))
				return false;
			return true;
		}
		
	}
	
	private static int _ids = 0;
	public static int makeAnId(){
		return ++ _ids;
	}
	
	void addVertex(){
		int id = makeAnId();
		addVertex(id);
	}
	void addVertex(int id){
		if( ! vertices.containsKey(id) ){
			Vertex v = new Vertex(id);
			vertices.put(id, v);
		}
	}
	void addVertex(Vertex v){
		if( this.vertices.containsKey(v.id) ){
			Vertex v1 = vertices.get(v.id);
			v1.edges.addAll(v.edges);
		}
	}
	void addEdge(Vertex a, Vertex b){
		Edge e = new Edge(a,b);
		a.edges.add(e);
		b.edges.add(e);
		edges.add(e);
	}
	void addEdge(Edge e){
		addVertex(e.head);
		addVertex(e.tail);
		e.head.edges.add(e);
		e.tail.edges.add(e);
		edges.add(e);
	}
	boolean hasEdge(Edge e){
		return  edges.contains(e);
	}
	boolean hasEdge(Vertex a, Vertex b){
		return (vertices.containsKey(a.id) && vertices.containsKey(b.id)
			&& vertices.get(a.id).hasNeighbor(b));
	}
	Vertex getVertex(int id){
		return ( vertices.containsKey(id))? vertices.get(id) : null;
	}
	boolean isConnected(){
		for( Vertex v : vertices.values()){
			if ( v.edges.size() == 0 )
				return false;
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Total Vertices = ").append(vertices.size())
		  .append(". Total Edges = ").append(edges.size())
		  .append('\n');
		for(Vertex v : vertices.values()){
			sb.append(v.toString()).append('\n');
		}
		return sb.toString();
	}
	
	public static AdjacencyListGraph makeRandomGraph(int vertexSize){
		AdjacencyListGraph G = new AdjacencyListGraph();
		for( int ii =0; ii< vertexSize ; ii++){
			G.addVertex();
		}
		for( int ii = 0;  ! G.isConnected();  ){
			int a = ( (int)(Math.random()*vertexSize) % vertexSize) + 1;
			int b = ( (int)(Math.random()*vertexSize) % vertexSize) + 1;
			if( a != b  && ! G.hasEdge(G.getVertex(a), G.getVertex(b)) ){
				G.addEdge(  new Edge( G.getVertex(a), G.getVertex(b) )  );
				ii ++;
			}
		}
		return G;
	}
	
	public static void main(String[] args) {
		AdjacencyListGraph G = makeRandomGraph(10);
		System.out.println(G);
	}
}
