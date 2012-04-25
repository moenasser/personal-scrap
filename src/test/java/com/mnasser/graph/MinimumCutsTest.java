package com.mnasser.graph;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mnasser.graph.Graph.Edge;

public class MinimumCutsTest {

	@Test
	public void testRandomEdgeSelect() throws Exception {
		Graph G = AdjacencyListGraph.makeRandomGraph(10);
		System.out.println(G);
		//System.out.println(G.toMatrixString());
		//System.out.println(G.isConnected());
		
		Map<Edge, Integer> edgeCounter = new HashMap<Edge, Integer>();
		for( Edge e : G.getEdges()) 
			edgeCounter.put(e, 0);
		for( int ii = 0; ii < 1000; ii ++){
			Edge e = MinimumCut.chooseRandomEdge(G);
			if(!edgeCounter.containsKey(e))edgeCounter.put(e, 0);
			edgeCounter.put(e, edgeCounter.get(e)+1);
		}
		System.out.println(edgeCounter);
	}
}
