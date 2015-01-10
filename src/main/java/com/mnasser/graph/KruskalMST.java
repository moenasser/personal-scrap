package com.mnasser.graph;

import java.util.Comparator;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;
import com.mnasser.util.Heap;

/**
 * Kruskal's Minimum Spanning Tree algorithm.
 * 
 * Using the Union-Find technique we can 
 * @author Moe
 */
public class KruskalMST {

	/**
	 * Given a graph <code>G</code>, attempts to procure a second graph 
	 * <code>T</code> that contains only the least amount of edges at the 
	 * smallest cost that will span all vertices in <code>G</code>.  
	 * This spanning tree is called the minimum cost spanning tree. 
	 * </p>
	 * The algo works by first sorting every edge <code>e</code> in <code>G</code>
	 * by edge cost; adding progressively higher cost edges one by one if and 
	 * only if they meet the following requirements :
	 * <ul>
	 * <li>edge does not form a closed circle</li>
	 * <li>vertices aren't already in our growing spanning tree</li>
	 * <li>edge is the smallest cost to vertex</li>
	 * </ul>
	 * We determine if a closed circle is to be formed by keeping track of the
	 * cluster group each vertex is being added to.  This is what the <code>leader 
	 * pointer</code> on each vertex is for. 
	 * 
	 * @param G
	 * @return T
	 */
	public static Graph findMST(Graph G){
		// Our MST
		Graph T = new AdjacencyListGraph();
		
		//Ranked edges by their costs
		Heap<Edge> sortedEdges = new Heap<Edge>(new Comparator<Edge>() {
			public int compare(Edge e1, Edge e2) {
				return e1.cost() - e2.cost();
			};
		});
		//Add edges to heap. (TODO : create "heapify" batch loading method)
		for( Edge e : G.getEdges()){
			sortedEdges.insert(e);
			e.src.visited = false;
			e.dst.visited = false;
			e.src.leaderPointer = e.src; // each has itself as leader pointer
			e.dst.leaderPointer = e.dst; // ie, its own cluster of 1
		}
		
		//begin our loop by adding in edges and adjusting leader pointers
		for( Edge e : sortedEdges ){
			
		}
		
		return T;
	}
	
	/** Finds the cluster group (connected components group) that <code>v</code>
	 * is a part of.*/
	protected static Vertex find(Vertex v){
		Vertex leader = v.leaderPointer;
		return  ( leader.leaderPointer != leader )? find(leader) : leader ;
		// in Lazy-Union-Find we need multiple 
	}
	
	/** Given the leaders of 2 connected component groups, will merge them into 
	 * 1 by updating all leader pointers of each group.*/
	protected static void union(Vertex v, Vertex u){
		Vertex leader = (v.followerCount >= u.followerCount)? v : u;
		Vertex follow = (u.followerCount < v.followerCount )? u : v;
		
		// TODO : how do we find all followers beneath a leader vertex?
		// TODO : must add follower array to each vertex
	}
}
