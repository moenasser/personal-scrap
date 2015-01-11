package com.mnasser.graph;

import org.junit.Test;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

/**
 * We'll run the Kruskal MST algorithm on a simple graph and assert that
 * it spits out the correct minimum spanning tree.
 * </p>
 * 
 * @author Moe
 */
public class KruskalMSTTest {

	@Test
	public void simpleTest(){
		Graph G = Graph.getGraphInstance();
		
		G.addEdge( new Edge( new Vertex(1), new Vertex(2) , 1) );
		G.addEdge( new Edge( new Vertex(1), new Vertex(3) , 5) );
		G.addEdge( new Edge( new Vertex(1), new Vertex(4) , 3) );
		G.addEdge( new Edge( new Vertex(1), new Vertex(5) , 4) );
		
		G.addEdge( new Edge( new Vertex(2), new Vertex(3) , 7) );
		G.addEdge( new Edge( new Vertex(3), new Vertex(4) , 6) );
		G.addEdge( new Edge( new Vertex(4), new Vertex(5) , 2) );
		
		System.out.println(G);
		
		Graph T = KruskalMST.findMST(G);
		
		System.out.println(T);
	}
}
