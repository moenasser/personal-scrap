package com.mnasser.graph;

import org.junit.Test;

public class DirectGraphTest {

	@Test
	public void testGraph() throws Exception {
		DirectedGraph g = new DirectedGraph();
		g.addEdge( 1, 2 );
		g.addEdge( 1, 3 );
		g.addEdge( 2, 3 );
		
		g.addEdge( 2, 4 );
		g.addEdge( 3, 4 );
		g.addEdge( 4, 1 );
		
		System.out.println(g);
		
		Graph g2 = (AdjacencyListGraph)Graph.copyOf(g);
		System.out.println(g2);
	}
}
