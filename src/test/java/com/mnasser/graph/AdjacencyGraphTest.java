package com.mnasser.graph;

import junit.framework.Assert;

import org.junit.Test;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class AdjacencyGraphTest {

	@Test
	public void testAddVertex() throws Exception {
		Graph g = new AdjacencyListGraph();
		Vertex a = new Vertex(1);
		g.addVertex( a );
		
		Assert.assertTrue( g.getVertices().contains(a) );
		Assert.assertTrue( g.getVertices().size() == 1 );
		Assert.assertTrue( g.getEdges().isEmpty() );
		Assert.assertEquals( g.getVertex(a) , a );
		Assert.assertEquals( g.getVertex(1) , a );
	}
	
	@Test
	public void testGraph() throws Exception {
		Graph g = new AdjacencyListGraph();
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
	}
	
	@Test
	public void testCopyOf() throws Exception {
		Graph g = new AdjacencyListGraph();
		g.addEdge( new Vertex(1), new Vertex(2) );
		g.addEdge( new Vertex(1), new Vertex(3) );
		g.addEdge( new Vertex(2), new Vertex(3) );
		g.addEdge( new Vertex(2), new Vertex(4) );
		g.addEdge( new Vertex(3), new Vertex(4) );
		
		Graph h = Graph.copyOf(g);
		Assert.assertEquals( h.toMatrixString(), g.toMatrixString());
		Assert.assertEquals( 4 , h.getVertices().size() );
		Assert.assertEquals( 5 , h.getEdges().size() );
	}
	
	@Test
	public void testVertex() throws Exception {
		Vertex a = new Vertex(1);
		Vertex b = new Vertex(2);
		Vertex c = new Vertex(1);
		
		Assert.assertFalse( a.equals(b) );
		Assert.assertFalse( b.equals(a) );
		
		Assert.assertTrue( a.equals(a) );
		Assert.assertTrue( b.equals(b) );
		
		Assert.assertTrue( a.equals(c) );
		Assert.assertTrue( c.equals(a) );
	}
	
	@Test
	public void testEdge() throws Exception {
		Vertex a = new Vertex(1);
		Vertex b = new Vertex(2);
		
		Edge e = new Edge(a, b);
		
		Assert.assertTrue( e.isIncidentOn(a) );
		Assert.assertTrue( e.isIncidentOn(b) );
		Assert.assertEquals( b , e.otherSide( a ) );
		Assert.assertEquals( a , e.otherSide( b ) );
		Assert.assertNull( e.otherSide( new Vertex(3) ) );
		Assert.assertFalse(e.isSelfLoop());
		Assert.assertNotNull(e.src);
		Assert.assertNotNull(e.dst);
		
		Edge e2 = new Edge( a, a );
		Assert.assertTrue(e2.isSelfLoop());
		
	}
	
	@Test
	public void testGraphEdges() throws Exception {
		Graph g = new AdjacencyListGraph();
		Vertex a = new Vertex(1);
		Vertex b = new Vertex(2);
		g.addVertex( a );
		g.addVertex( b );
		Assert.assertTrue( g.getVertices().contains(a) );
		Assert.assertTrue( g.getVertices().contains(b) );
		Assert.assertTrue( g.getEdges().isEmpty() );
		Assert.assertEquals( 2, g.getVertices().size() );
		Assert.assertEquals( a, g.getVertex(a) );
		Assert.assertEquals( b, g.getVertex(b) );
		
		Assert.assertTrue( g.hasVertex(a) );
		Assert.assertTrue( g.hasVertex(b) );
		Assert.assertTrue( g.hasVertex(1) );
		Assert.assertTrue( g.hasVertex(2) );
		
		g.addEdge( a, b );
		Assert.assertFalse( g.getEdges().isEmpty() );
		Assert.assertEquals( 1, g.getEdges().size() );
		g.addEdge( a, b ); // parallel edges
		Assert.assertEquals( 2, g.getEdges().size() );
		
		
		Edge e = g.getEdge(a, b);
		Assert.assertEquals( e, g.getVertex(b).getEdge(a) );
		Assert.assertTrue( g.getEdges().contains(e) );
		Assert.assertEquals( g.getEdge(a, b), g.getEdge(1, 2) );
		Assert.assertTrue( g.hasEdge(e) );
		Assert.assertEquals( g.hasEdge(a, b), g.hasEdge(1, 2) );
		
		a = g.getVertex(a);
		b = g.getVertex(b);
		Assert.assertTrue( a.hasEdge(e) );
		Assert.assertEquals( b, a.getNeighbor(e));
		
		
		g.removeEdge(e);
		Assert.assertEquals( 1, g.getEdges().size() );
		Assert.assertTrue( g.hasEdge(e) );
		
		g.removeEdge(e);
		Assert.assertEquals( 0, g.getEdges().size() );
		Assert.assertFalse( g.hasEdge(e) );
		
		g.addEdge(e);
		Assert.assertEquals( 1, g.getEdges().size() );
		
		g.removeVertex(a);
		Assert.assertEquals( 1, g.getVertices().size() );
		Assert.assertFalse( g.getVertices().contains(a) );
		Assert.assertTrue( g.getEdges().isEmpty() );
		Assert.assertNull( g.getVertex(b).getEdge(a) );
		
		g.addVertex(a);
		g.addEdge(e);
		Assert.assertEquals( e, g.getVertex(b).getEdge(a) );
		Assert.assertTrue( g.getVertex(b).hasNeighbor(a) );
	}
}
