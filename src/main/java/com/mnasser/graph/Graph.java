package com.mnasser.graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
	
	public abstract List<Vertex> getVertices();
	public abstract List<Edge> getEdges();
	
	public abstract void addVertex(Vertex v);
	public abstract void addEdge(Vertex a, Vertex b);
	public abstract void addEdge(Edge e);
	public abstract boolean hasVertex(Vertex a);
	public abstract boolean hasVertex(int id);
	public abstract boolean hasEdge(Edge e);
	public abstract boolean hasEdge(Vertex a, Vertex b);
	public abstract boolean hasEdge(int a, int b);
	public abstract Vertex getVertex(int id);
	public abstract Vertex getVertex(Vertex a);
	public abstract Edge getEdge(Vertex a, Vertex b);
	public abstract Edge getEdge(int a, int b);
	
	public abstract boolean isConnected();
	
	public abstract void removeVertex(Vertex v);
	public abstract void removeEdge(Edge e);
	
	private static int _ids = 0;
	public static int makeAnId(){
		return ++ _ids;
	}
	

	public static class Vertex implements Comparable<Vertex> {
		public final int id;
		public List<Edge> edges;
		public Vertex(int id) {
			this.id = id;
			this.edges = new ArrayList<Edge>();
		}
		Edge getEdge(Vertex b){
			for( Edge e : edges ){
				if( b.equals( e.otherSide(this)) )
					return e;
			}
			return null;
		}
		void removeEdge(Edge e){
			if( edges.contains(e) ){
				edges.remove(e);
			}
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
				Vertex o = e.otherSide(this);
				if( o == null ){
					throw new RuntimeException("@ vert :" + this + " on Edge "+ e);
				}
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
		@Override
		public int compareTo(Vertex o) {
			return Integer.valueOf(this.id).compareTo(o.id);
		}
	}
	
	
	public static class Edge   {
		final Vertex head, tail;
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
		public String toString(){
			return "("+head.id +","+tail.id+")";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (head.hashCode() + tail.hashCode());
			//result = prime * result + ((tail == null) ? 0 : tail.hashCode());
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
			if( head == other.head || head == other.tail ){
				if( tail == other.tail || tail == other.head )
					return true;
			}
			return false;
			/*
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
			*/
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Total Vertices = ").append(getVertices().size())
		  .append(". Total Edges = ").append(getEdges().size())
		  .append(". Connected = " + isConnected())
		  .append('\n');
		Vertex _v = null;
		try{
			for(Vertex v : getVertices()){
				_v = v;
				sb.append(v.toString()).append('\n');
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(_v);
		}
		return sb.toString();
	}
	
	public String toMatrixString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Total Vertices = ").append(getVertices().size())
		  .append(". Total Edges = ").append(getEdges().size())
		  .append(". Connected = " + isConnected())
		  .append('\n');
		sb.append("\t");
		//for( int ii=0;ii<getVertices().size();ii++){
		for( Vertex v : getVertices() ){
			sb.append("  ").append(v.id).append(' ');
		}
		sb.append("\n");
		//for( int ii=0; ii< getVertices().size();ii++){
		for( Vertex ii : getVertices() ){
			sb.append(ii.id + " :\t|");
			//for( int jj=0; jj< getVertices().size();jj++){
			for( Vertex jj : getVertices() ){
				sb.append(' ');
				if( hasEdge(ii, jj) ){
					sb.append('X');
				}else{
					sb.append('_');
				}
				sb.append(' ');
				sb.append('|');
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
