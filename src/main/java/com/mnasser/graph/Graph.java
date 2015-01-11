package com.mnasser.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic representation of a graph.  It consists of edges E and vertices V.
 * 
 * @author Moe
 */
public abstract class Graph {

	/**Factory constructor**/
	public static Graph getGraphInstance(){
		return new AdjacencyListGraph();
	}
	
	public abstract List<Vertex> getVertices();
	public abstract List<Edge> getEdges();
	
	public abstract Vertex addVertex(Vertex v);
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
	
	public boolean isDirected(){ return false ; }
	public abstract boolean hasDisjointNodes();
	
	public abstract int getEdgeCount();
	public abstract int getVertexCount();
	
	
	public abstract void removeVertex(Vertex v);
	public abstract void removeEdge(Edge e);
	
	private static int _ids = 0;
	protected static int makeAnId(){
		return ++ _ids;
	}
	
	public static Graph copyOf(Graph g){
		Graph q = new AdjacencyListGraph();
		for( Edge e : g.getEdges() ){
			q.addEdge(e);
		}
		return q;
	}

	/**
	 * Logical representation of a vertex point on a graph. 
	 * </p>
	 * Keeps a 
	 * @author Moe
	 */
	public static class Vertex implements Comparable<Vertex> {
		/**TODO : allow for parameterized IDs and/or contents.
		 * ex: String, Integer, Object etc. This will mean the parameterized
		 * contents will have to be comparable and define hashCode & equals.*/
		public final int id;
		
		protected List<Edge> edges;
		protected boolean visited = false;
		protected int order = -1;
		protected boolean directed = false;
		
		/**The leader pointer is used for Union-Find algorithms
		 * By default each vertex is in its own cluster and thus is its
		 * own leader pointer*/
		protected Vertex leaderPointer = this;
		/**How many other vertices point to this vertex.*/
		protected List<Vertex> followers = null;
		
		public Vertex(int id) {
			this.id = id;
			this.edges = new ArrayList<Edge>();
		}
		/**Given another vertex <code>b</code> returns the edge
		 * incident on both us and <code>b</code>.  Returns null otherwise.*/
		public Edge getEdge(Vertex b){
			for( Edge e : edges ){
				if( b.equals( e.otherSide(this)) )
					return e;
			}
			return null;
		}
		/**Returns a copy of all edges incident on this vertex */
		public List<Edge> getEdges(){
			// make copy
			List<Edge> l = new ArrayList<Edge>();
			l.addAll(edges);
			return l;
		}
		/**Given an edge <code>e</code> returns the vertex opposite us on the 
		 * other side of <code>e</code>. */
		public Vertex getNeighbor(Edge e){
			if( hasEdge(e) ){
				return e.otherSide(this);
			}
			return null;
		}
		boolean hasEdge(Edge e){
			return edges.contains(e);
		}
		void removeEdge(Edge e){
			if( edges.contains(e) ){
				edges.remove(e);
			}
		}
		/**Returns true iff there is an edge incident on both this vertex 
		 * and <code>b</code>*/
		boolean hasNeighbor(Vertex b){
			for(Edge e : edges){
				if( b.equals( e.otherSide(this) ))
					return true;
			}
			return false;
		}
		/**Returns the number of edges incident on both this vertex and on
		 * the given vertex <code>b</cdoe>*/
		int numEdges(Vertex b ){
			if( b == null )	return 0; 
			int ii = 0;
			for(Edge e : edges){
				if( e.otherSide(this).equals(b) ) 
					ii ++;
			}
			return ii;
		}
		/**Returns a list of all edges where this vertex is a source vertex. */
		List<Edge> getOutBound(){
			List<Edge> out = new ArrayList<Edge>();
			for( Edge e : edges ){
				if( e.src.equals(this) && ! e.dst.equals(this) ){
					out.add(e);
				}
			}
			return out;
		}
		/**Returns a list of all edges where this vertex is a destination vertex.*/
		List<Edge> getInBound(){
			List<Edge> in = new ArrayList<Edge>();
			for( Edge e : edges ){
				if( ! e.src.equals(this) && e.dst.equals(this) ){
					in.add(e);
				}
			}
			return in;
		}
		/**Returns true if this vertex has been visited.*/
		boolean isVisited(){ return this.visited; }
		boolean isVisited(Edge e, boolean reverse){
			if( ! e.isIncidentOn(this) ){
				throw new RuntimeException("I couldn't have traveresed an edge not incident on me!");
			}
			if( ( ! e.src.equals(this) && ! reverse) || ( reverse && ! e.dst.equals(this) ) ){
				throw new RuntimeException("Can't check visited if I'm the "+((reverse)?"reverse":"")+" end of the edge");
			}
			return (reverse)? e.src.isVisited() : e.dst.isVisited();
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(id).append(" "+((order==-1)?"":"(#"+order+")")+" -> [");
			List<Edge> es = (this.directed)? getOutBound() : edges;
			for( Edge e : es ){
				Vertex o = e.otherSide(this);
				if( o == null ){
					throw new RuntimeException("@ vert :" + this + " on Edge "+ e);
				}
				sb.append( o.id )
				  .append(',');
			}
			if( es.size()>0 ) sb.deleteCharAt(sb.length()-1);
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
		public int compareTo(Vertex o) {
			return Integer.valueOf(this.id).compareTo(o.id);
		}
	}
	/**Logical representation of an edge between to vertices on a graph.
	 * Can be weighted by given it a cost during construction.  Can be thought
	 * of as being directed since it can remember the vertices as source and 
	 * destination points.  
	 * */
	public static class Edge   {
		final Vertex src, dst;
		private int cost;
		public Edge(Vertex a, Vertex b) {
			if( a == null || b == null )
				throw new RuntimeException("Can't have null vertices in and edge : ("
						+((a==null)?"null":a.id) +','+((b==null)?"null":b.id) );
			this.src = a;
			this.dst = b;
		}
		public Edge(Vertex a, Vertex b, int cost){
			this(a,b);
			this.cost = cost;
		}
		/**Returns the vertex at the source end of this edge */
		public Vertex getSrcVertex(){ return src; }
		/**Returns the vertex at the destination end of this edge */
		public Vertex getDstVertex(){ return dst; }
		
		/**Vertices use this to get what's on the other side of the edge*/
		public Vertex otherSide(Vertex head){
			if( this.src.equals(head) )
				return this.dst;
			if( this.dst.equals(head) )
				return this.src;
			return null;
		}
		/**Returns true iff this edge touches vertex <code>a</code>.*/
		public boolean isIncidentOn( Vertex a ){
			return  src.equals(a) || dst.equals(a);
		}
		/**Returns a string representing the endpoints along this edge in the 
		 * following format : <code>(src,dst)</code>*/
		public String toString(){
			return "("+src.id +","+dst.id+")"+cost;
		}
		/**Returns true iff this edge's end points are one and the same*/
		public boolean isSelfLoop(){
			return src.equals(dst);
		}
		/**Returns an edge (not currently connected to the graph) with its
		 * source and destination reversed.  
		 */
		public Edge reverse(){
			return new Edge(dst, src, cost);
		}
		/**Returns the cost of traversing this edge if this is to be 
		 * used in a weighted graph*/
		public int cost(){
			return cost;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (src.hashCode() + dst.hashCode());
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
			if( src.equals(other.src) ){
				return dst.equals(other.dst);
			}
			if( src.equals(other.dst) ){
				return dst.equals(other.src);
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
	/**Returns a randomly selected edge from graph <code>G</code>. */
	public static Edge chooseRandomEdge(Graph G){
		int esize = G.getEdges().size();
		if( esize == 0 ) throw new RuntimeException("Attempt to choose an edge from graph w/ no edges!");//return null;
		int idx = (int)(Math.random() * esize) % esize;
		return G.getEdges().get(idx);
	}
	/**If graph <code>G</code> is no empty, returns a randomly selected vertex */
	public static Vertex chooseRandomVertex(Graph G){
		int esize = G.getVertices().size();
		if( esize == 0 )throw new RuntimeException("Attempt to choose a vertex from empty graph!");
		int idx = (int)(Math.random() * esize) % esize;
		return G.getVertices().get(idx);
	}
	

	
	public String toAdjListString() {
		StringBuilder sb = new StringBuilder();
		sb.append(toInfoLine());
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
	
	@Override
	public String toString(){
		return toMatrixString() +  toAdjListString();
	}
	protected String toInfoLine(){
		StringBuilder sb = new StringBuilder();
		sb.append("Total Vertices = ").append(getVertices().size())
		  .append(". Total Edges = ").append(getEdges().size())
		  .append(". Connected = " + hasDisjointNodes())
		  .append('\n');
		return sb.toString();
	}
	public String toMatrixString() {
		StringBuilder sb = new StringBuilder();
		sb.append(toInfoLine()).append('\t');
		for( Vertex v : getVertices() ){
			sb.append("  ").append(v.id).append(' ');
		}
		sb.append("\n");
		for( Vertex ii : getVertices() ){
			sb.append(ii.id + " :\t|");
			for( Vertex jj : getVertices() ){
				sb.append(' ');
				if( hasEdge(ii, jj) ){
					Edge e = getEdge(ii, jj);
					if( ii.equals(e.src) || ! isDirected() )
						sb.append('X');
					else sb.append('O');
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
