package com.mnasser.graph;

import java.util.ArrayList;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;
import com.mnasser.util.Heap;

/**
 * Kruskal's Minimum Spanning Tree algorithm.
 * 
 * Using the Union-Find technique we can approach O(mlogn) worst case 
 * in finding the minimum set of edges that spans all vertices in a given
 * graph <code>G</code>.
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
	 * </p>
	 * NOTE : This algorithm is "naive" only that we could do better than 
	 * {@code O(mlogn)} upper bound.
	 * 
	 * @param G A graph whose MST is desired
	 * @return T The minimum spanning tree of {@code G}
	 * @see findMST
	 */
	public static Graph findMSTNaive(Graph G){
		// Our MST
		Graph T = new AdjacencyListGraph();
		
		//Ranked edges by their costs
		Heap<Edge> edgeHeap = new Heap<Edge>(Graph.getEdgeComparator());
		
		long start = System.currentTimeMillis();
		
		//Initialization ...
		//Add edges to heap. (TODO : create "heapify" batch loading method)
		for( Edge e : G.getEdges()){
			edgeHeap.insert(e);
			e.src.visited = false;
			e.dst.visited = false;
			e.src.leaderPointer = e.src; // each has itself as leader pointer
			e.dst.leaderPointer = e.dst; // ie, its own cluster of 1
			e.src.followers = null;
			e.dst.followers = null;
			//if (e.src.followers==null) e.src.followers = new ArrayList<Vertex>();
			//if (e.dst.followers==null) e.dst.followers = new ArrayList<Vertex>();
		}
		
		long lap = System.currentTimeMillis();
		System.out.println("Time to prep G : " + (lap - start) );
		
		start = System.currentTimeMillis();
		
		//begin our loop by adding in edges and adjusting leader pointers
		while ( edgeHeap.size() > 0 ) {
			//System.out.println( edgeHeap );
			Edge e = edgeHeap.removeRoot();
			
			// since both of these are in connected component groups,
			// we need to find their leader pointers.  If both Nodes are 
			// already in the same leader pointer group then adding 
			// this edge will cause a CYCLE.
			Vertex cluster1 = find( e.src );
			Vertex cluster2 = find( e.dst );
			
			if(  cluster1  !=  cluster2  ) 
			{
				// add the edge + vertices to T
				T.addEdge( e );
				// make sure they are in the same group
				union( cluster1 ,  cluster2 );
			}	
			
			if ( T.getVertexCount() == G.getVertexCount() ) // we're done. 
				break; // All vertices have been added to the MST. So stop early
		}
		
		lap  = System.currentTimeMillis();
		System.out.println("Time to find MST : " + (lap - start));
		
		return T;
	}
	
	/** Finds the cluster group (connected components group) that <code>v</code>
	 * is a part of.*/
	protected static Vertex find(Vertex v){
		Vertex leader = v.leaderPointer;
		return  ( leader.leaderPointer != leader )? find(leader) : leader ;
		// in Lazy-Union-Find we need multiple recursive calls to find()
	}
	
	/** Given the leaders of 2 connected component groups, will merge them into 
	 * 1 by updating all leader pointers of each group.*/
	protected static void union(Vertex v, Vertex u){
		Vertex leader = null , follower = null;
		if( getFollowerSize(v) >= getFollowerSize(u) ){
			leader = v;  follower = u;
		}else{
			leader = u;  follower = v;
		}
		//Vertex leader = (v.followers.size() >= u.followers.size())? v : u;
		//Vertex follower = (u.followers.size() < v.followers.size() )? u : v;
		
		if ( leader.followers == null )
			leader.followers = new ArrayList<Vertex>();
		
		// NOTE: This will create a "flat" tree of followers 1-level below the leader
		// For Lazy-Union-Find you would allow multiple levels.
		if ( follower.followers != null ) {
			for( Vertex ff : follower.followers ){
				ff.leaderPointer = leader;
				if ( ff.followers != null ){
					ff.followers.clear(); // for Lazy-Union-Find, allow follower to retain followers
					ff.followers = null;
				}
			}
			leader.followers.addAll(follower.followers);
			follower.followers.clear();
			follower.followers = null;
		}
		
		leader.followers.add( follower );
		follower.leaderPointer = leader;
		// TODO : how do we find all followers beneath a leader vertex?
		// TODO : must add follower array to each vertex
	}
	
	private static int getFollowerSize(Vertex v){
		return (v.followers == null) ? 0 : v.followers.size();
	}
}
