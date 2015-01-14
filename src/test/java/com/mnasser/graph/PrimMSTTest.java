package com.mnasser.graph;

import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Test;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class PrimMSTTest {

	@Test
	public void simpleTest(){
		Graph G = Graph.getInstance();
		
		G.addEdge( new Edge( new Vertex(1), new Vertex(2) , 1) );
		G.addEdge( new Edge( new Vertex(1), new Vertex(3) , 5) );
		G.addEdge( new Edge( new Vertex(1), new Vertex(4) , 3) );
		G.addEdge( new Edge( new Vertex(1), new Vertex(5) , 4) );
		
		G.addEdge( new Edge( new Vertex(2), new Vertex(3) , 7) );
		G.addEdge( new Edge( new Vertex(3), new Vertex(4) , 6) );
		G.addEdge( new Edge( new Vertex(4), new Vertex(5) , 2) );
		
		System.out.println(G);
		
		Graph T = PrimMST.findMSTNaive(G);
		
		System.out.println(T);
		
		Assert.assertTrue( T.getEdgeCount() == 4);
		Assert.assertTrue( T.getVertexCount() == 5 );
		
	}
	
	@Test
	public void largeMSTtest() throws IOException, URISyntaxException{
		long start = System.currentTimeMillis();
		Graph G = KruskalMSTTest.loadTestGraph("edges_graph.txt");
		
		long fileLoad = System.currentTimeMillis();
		Graph T = PrimMST.findMSTNaive( G );
		
		long mstTime = System.currentTimeMillis();
		
		System.out.printf("MST edges : %s , nodes : %s%n", T.getEdgeCount(), T.getVertexCount());
		
		System.out.println("Time to load graph : " + (fileLoad-start));
		System.out.println("Time to find MST   : " + (mstTime-fileLoad));
	
		int total_cost = 0;
		for( Edge e : T.getEdges() )
			total_cost += e.cost();
		
		System.out.println("Total cost of MST  : " + total_cost);
	}

}
