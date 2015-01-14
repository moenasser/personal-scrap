package com.mnasser.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;

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
		Graph G = Graph.getInstance();
		
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
		
		Assert.assertTrue( T.getEdgeCount() == 4);
		Assert.assertTrue( T.getVertexCount() == 5 );
	}
	
	@Test
	public void largeMSTtest() throws IOException, URISyntaxException{
		long start = System.currentTimeMillis();
		Graph G = loadTestGraph("edges_graph.txt");
		
		long fileLoad = System.currentTimeMillis();
		Graph T = KruskalMST.findMST( G );
		
		long mstTime = System.currentTimeMillis();
		
		System.out.printf("MST edges : %s , nodes : %s%n", T.getEdgeCount(), T.getVertexCount());
		
		System.out.println("Time to load graph : " + (fileLoad-start));
		System.out.println("Time to find MST   : " + (mstTime-fileLoad));
	
		int total_cost = 0;
		for( Edge e : T.getEdges() )
			total_cost += e.cost();
		
		System.out.println("Total cost of MST  : " + total_cost);
	}
	
	/*Looks in resources/ dir for MST test graph edge/vertices info*/
	static Graph loadTestGraph(String file) throws IOException, URISyntaxException{
		File f = new File( KruskalMSTTest.class.getResource(file).toURI() );
		System.out.print("Loading file " + file + "...");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = null;
		
		line = br.readLine(); // first line is edge/vert counts.
		String[] counts = line.trim().split("\\s+");
		int total_nodes = Integer.parseInt(counts[0]);
		int total_edges = Integer.parseInt(counts[1]);
		System.out.printf("Graph should have %s vertices & %s edges.%n", total_nodes, total_edges);
		
		int cnt = 0;
		Graph G = new AdjacencyListGraph();
		while( (line=br.readLine())!=null){
			cnt++;
			String[] edge = line.trim().split("\\s+");
			Vertex a = new Vertex(Integer.parseInt(edge[0]));
			Vertex b = new Vertex(Integer.parseInt(edge[1]));
			int cost = Integer.parseInt(edge[2]);
			Edge e = new Edge( a, b, cost );
			if( ! G.hasEdge(e) )
				G.addEdge( e );
		}
		System.out.println(cnt + " total lines read.");
		System.out.println(G.toInfoLine());
		return G;
	}

}
