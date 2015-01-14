package com.mnasser.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class MinimumCutsTest {

	@Test
	public void testRandomEdgeSelect() throws Exception {
		Graph G = Graph.makeRandomGraph(5);
		//System.out.println(G);
		//System.out.println(G.toMatrixString());
		//System.out.println(G.isConnected());
		
		Map<Edge, Integer> edgeCounter = new HashMap<Edge, Integer>();
		for( Edge e : G.getEdges()) 
			edgeCounter.put(e, 0);
		for( int ii = 0; ii < 10000; ii ++){
			Edge e = Graph.chooseRandomEdge(G);
			if(!edgeCounter.containsKey(e))edgeCounter.put(e, 0);
			edgeCounter.put(e, edgeCounter.get(e)+1);
		}
		int sum = 0;
		for( Integer ii : edgeCounter.values() ){
			sum += ii;
		}
		double mean = sum / edgeCounter.values().size();
		double sqrDiffSum = 0;
		for( Integer ii : edgeCounter.values() ){
			sqrDiffSum += Math.pow( (double)(mean - ii), 2 );
		}
		double variance = sqrDiffSum / edgeCounter.values().size();
		double stdDev = Math.sqrt(variance);
		System.out.println(edgeCounter);
		System.out.println("mean = " + mean + ". variance = " + variance+". Std Deviation = " + stdDev);
		
		for( Integer ii : edgeCounter.values() ){
			Assert.assertTrue( Math.abs(mean - ii) <= (3*stdDev) );
		}
	}
	
	@Test
	public void testRemoveRandomEdge() throws Exception {
		Graph g = Graph.makeRandomGraph(5);
		
		Edge e = Graph.chooseRandomEdge(g);
		Assert.assertTrue( g.hasEdge(e) );
		
		g.removeEdge(e);
		
		Assert.assertFalse( g.hasEdge(e) );
		Assert.assertFalse( e.src.hasEdge(e) );
		Assert.assertFalse( e.dst.hasEdge(e) );
	}
	
	@Test
	public void testRemoveRandomVertex() throws Exception {
		Graph g = Graph.makeRandomGraph(5);
		
		Vertex a = Graph.chooseRandomVertex(g);
		Assert.assertTrue(g.hasVertex(a));
		
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for( Edge e : a.edges ){
			neighbors.add( e.otherSide(a) );
		}
		
		g.removeVertex(a);
		Assert.assertFalse(g.hasVertex(a));
		
		for( Vertex neighbor : neighbors ){
			Assert.assertFalse( neighbor.hasNeighbor(a) );
			Assert.assertFalse( g.hasEdge( neighbor, a) );
			Assert.assertFalse( g.hasEdge( a, neighbor) );
		}
	}
	
	@Test
	public void testContractEdge() throws Exception {
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
		
		MinimumCut.contractEdge(g, e);
		
		Assert.assertFalse( g.hasEdge(e) );
		int min = Math.min( e.src.id, e.dst.id);
		int max = Math.max( e.src.id, e.dst.id);
		Assert.assertFalse( g.hasVertex( new Vertex(max)) );
		Assert.assertTrue( g.hasVertex( new Vertex(min)) );
		
		Assert.assertEquals( 4, g.getEdges().size() );
		Assert.assertEquals( 3, g.getVertices().size() );
		
		Assert.assertEquals(3, g.getVertex(min).edges.size() );
		
		Vertex one = g.getVertex(min);
		
		Assert.assertEquals( 2 , one.numEdges(g.getVertex(3)) );
		Assert.assertEquals( 1 , one.numEdges(g.getVertex(4)) );
		Assert.assertEquals( 1 , g.getVertex(4).numEdges(g.getVertex(3)) );
		
		e = new Edge( new Vertex(1), new Vertex( 3 ) );
		
		MinimumCut.contractEdge(g, e);
		
		one = g.getVertex(min);
		
		Assert.assertEquals( 0 , one.numEdges(g.getVertex(3)) );
		Assert.assertEquals( 2 , one.numEdges(g.getVertex(4)) );
		Assert.assertEquals( 0 , g.getVertex(4).numEdges(g.getVertex(3)) );
		Assert.assertEquals( 2 , g.getVertex(4).numEdges( one ) );
		Assert.assertEquals( 2,  g.getVertices().size() );
	}

	@Test
	public void testFindMinimumCut() throws Exception {
		Graph g = new AdjacencyListGraph();
		g.addEdge( new Vertex(1), new Vertex(2) );
		g.addEdge( new Vertex(1), new Vertex(3) );
		g.addEdge( new Vertex(2), new Vertex(3) );
		g.addEdge( new Vertex(2), new Vertex(4) );
		g.addEdge( new Vertex(3), new Vertex(4) );
		
		int minCuts = MinimumCut.findPossibleMinimumCut(g);
		
		Assert.assertEquals( 2 , g.getVertices().size() );
		//Assert.assertEquals( 2 , minCuts ); // cannot assert this is true. could be 3 or 2
		Assert.assertEquals( minCuts , g.getEdges().size() );
	}
	
	@Test
	public void testMinCuts() throws Exception {
		//Graph g = MinimumCut.loadGraph("/home/mnasser/workspace/personal-scrap/src/main/resources/kargerTest.txt");
		Graph g = MinimumCut.loadGraph("kargerTest.txt");
		System.out.println( "Min cut = " +  MinimumCut.repeatFindMinimumCut(g, 1000) );
	}

}
