package com.mnasser.graph;

import org.junit.Assert;
import org.junit.Test;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class DFSTest {

	@Test
	public void testDfs(){
		DirectedGraph g = new DirectedGraph();
		
		g.addEdge( new Vertex(1), new Vertex(2) );
		g.addEdge( new Vertex(1), new Vertex(3) );
		g.addEdge( new Vertex(2), new Vertex(3) );
		g.addEdge( new Vertex(2), new Vertex(4) );
		g.addEdge( new Vertex(3), new Vertex(4) );
		
		Assert.assertEquals( 5, g.getEdges().size() );
		Assert.assertEquals( 4, g.getVertices().size() );
		
		Edge e = new Edge(new Vertex(1), new Vertex(2));
		Assert.assertTrue( g.hasEdge(e) );
	
		Vertex one = g.getVertex(1);
		Assert.assertEquals( 2, one.edges.size() );
		
		System.out.println(one.getOutBound());
		System.out.println(g.toString());
		
		System.out.println("=========TRAVERSING======");
		for( Vertex v : g.getVertices() ) {
			System.out.println("\nStarting at " + v);
			g.clearVisited();
			DFS.traverseDFS( v );
		}
		
	}
}
