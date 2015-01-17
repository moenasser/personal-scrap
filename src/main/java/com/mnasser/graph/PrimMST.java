package com.mnasser.graph;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;
import com.mnasser.util.Heap;

/**
 * Implementation of Prim's Minimum Spanning Tree algorithm.
 * </p>
 * Uses a heap to keep track of next cheapest edge which will introduce 
 * a new vertex into our growing spanning tree.  
 *
 * @author Moe
 */
public class PrimMST {

	/**
	 * Naive (straightforward) implementation of Prim's MST.
	 * </p> 
	 * If given a connected graph <code>G</code>, returns a minimum spanning 
	 * tree, <code>T</code>, which is guaranteed to only contain those edges 
	 * in <code>G</code> which will span all vertices with the least cost. 
	 * </p>
	 * Algorithm is considered naive since it could have better constant time 
	 * costs.  Big-Oh is still <code>O(m log n)</code>, where {@code m} is number
	 * of edges and {@code n} is number of vertices. 
	 * @param G A connected graph 
	 * @return A Minimum Spanning Tree of G
	 */
	public static Graph findMSTNaive(Graph G){
		
		// The group of all edges seen by all vertices currently being added
		// to our MST graph T.  
		Heap<Edge> frontier = new Heap<Edge>( Graph.getEdgeComparator(), false );
		
		// our MST. Will fill slowly with vertices
		Graph T = Graph.getInstance();
		
		// TODO : this should be in an initialization step of the graph itself
		for( Vertex v : G.getVertices() )
			v.visited = false;
		
		Vertex current = Graph.getRandomVertex( G );
		current.visited = true; // mark
		T.addVertex(current);   // add to temp graph
		
		do {
			
			
			for( Edge e : current.getEdges() )
				frontier.insert( e );
			
			// go thru the best (cheapest cost) edges and find the next one 
			// that introduces a new vertex not already in T.
			// If an edge introduces a visited vertex just skip it.
			Edge bestEdge;
			do 
			{
				bestEdge = frontier.removeRoot(); // get next cheapest edge
				
				// has to introduce at least 1 new vertex, else skip this edge
			}while( bestEdge.dst.isVisited() && bestEdge.src.isVisited() );
			
			// if this edge was a good edge, add it to T and set current to new vertex
			current = getUnvisited( bestEdge );
			T.addVertex( current );
			T.addEdge( bestEdge );
			current.visited = true;
			
		}while( T.getVertexCount() != G.getVertexCount() );
		
		
		return T;
	}
	
	private static Vertex getUnvisited(Edge e){
		return e.dst.isVisited() ? e.src : e.dst;
	}
}
