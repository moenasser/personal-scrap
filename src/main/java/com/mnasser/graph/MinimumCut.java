package com.mnasser.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.mnasser.DirUtils;
import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class MinimumCut {

	public static void main(String[] args) throws IOException, URISyntaxException {
		Graph G = Graph.makeRandomGraph(5);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		Edge e = Graph.chooseRandomEdge(G);
		System.out.println(e);
		G.removeEdge(e);
		System.out.println(G.toMatrixString());
		System.out.println(G);

		G.addEdge(e);
		G.addEdge(e);
		G.addEdge(e);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		Vertex v = Graph.chooseRandomVertex(G);
		System.out.println(v);
		G.removeVertex(v);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		
		System.out.println("Contracting edge TEST ==========");
		G = Graph.makeRandomGraph(5);
		e = Graph.chooseRandomEdge(G);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		System.out.println("Contracting edge " + e +" ... ");
		
		contractEdge(G, e);
		
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		for( int ii =2; ii < G.getEdges().size(); ii++){
			System.out.println("==========  " + ii + " ==========");
			e = Graph.chooseRandomEdge(G);
			contractEdge(G, e);
			System.out.println("Contracting edge " + e);
			
			System.out.println(G.toMatrixString());
			System.out.println(G);
		}

		Graph K = loadGraph(DirUtils.getWorkDir() + "kargerAdj.txt");
		//System.out.println("Karver graph = \n"+K.toMatrixString());
		//System.out.println(K);
		System.out.println("First Graph size : " + K.getVertices().size());
		System.out.println("Example random minimum cut size : " + findPossibleMinimumCut(K));
		
		K = loadGraph(DirUtils.getWorkDir() +  "kargerAdj.txt");
		int lowestMinSeen = Integer.MAX_VALUE;
		for( int ii = 0; ii < 5; ii ++ ){
			int min = repeatFindMinimumCut(K, 1000);
			if( min < lowestMinSeen )
				lowestMinSeen = min;
		}
		System.out.println("Lowest minimum seen was = " + lowestMinSeen + '\n');

		
		Graph K2 = loadGraph(DirUtils.getWorkDir() +  "kargerMinCut.txt");
		System.out.println(K);
		System.out.println("2nd Graph size : " + K2.getVertices().size());
		lowestMinSeen = Integer.MAX_VALUE;
		for( int ii = 0; ii < 5; ii ++ ){
			int min = repeatFindMinimumCut(K2, 100); // very slow. so do 100 at a time (~58sec on MacBook Pro 2009, dual-core 2.8 GHz)
			if( min < lowestMinSeen )
				lowestMinSeen = min;
		}
		System.out.println("Lowest minimum seen was = " + lowestMinSeen);
		
	}
	
	static int repeatFindMinimumCut(Graph g, int reps){
		long time = System.currentTimeMillis();
		System.out.print("Attempting to find a possible minimum cut ("+reps+" repititions)... ");
		int minCut = Integer.MAX_VALUE;
		for( int ii = 0; ii < reps; ii++ ){
			Graph h = Graph.copyOf(g);
			int min = findPossibleMinimumCut(h);
			if( min < minCut){
				minCut = min;
			}
		}
		time = ( System.currentTimeMillis() - time );
	    System.out.println("Lowest min seen so far " + minCut + ". ("+(((double)time/1000))+" sec)");
		return minCut;
	}
	
	// Randomonly finds what could be the minimum cut
	static int findPossibleMinimumCut(Graph g){
		//System.out.println("========== Finding minimum cut ");
		while( g.getVertices().size() > 2 ){
			Edge e = Graph.chooseRandomEdge(g);
			//System.out.println("Contracting edge " + e);
			contractEdge(g, e);
			if( g.getVertices().size() == 3 || g.getVertices().size() == 4 ){
				g.toMatrixString();
			}
			//System.out.println(g);
		}
		//System.out.println("Minimum Edges left = " + g.getEdges().size());
		return g.getEdges().size();
	}
	
	
	static void contractEdge(Graph g, Edge e){
		if( ! g.hasEdge(e) ) throw new RuntimeException("Graph does not have edge "+e);
		// grab all other edges from vertex a and vertex b.
		// create a new vertex c with all the remaining edges of a & b
		// delete self loops.
		Vertex a = g.getVertex(e.src); // hold references for later use
		Vertex b = g.getVertex(e.dst);

		List<Edge> allEdges = new ArrayList<Edge>();
		allEdges.addAll(a.edges);
		for( Edge _b : b.edges ){
			if( ! _b.isIncidentOn(a) )
				allEdges.add( _b );
		}
		/*
		Map<Edge, Integer> allEdges = new HashMap<Edge, Integer>();
		for( Edge _a : a.edges ){
			if( ! allEdges.containsKey( _a ) ) allEdges.put( _a, 1);
			allEdges.put( _a , allEdges.get(_a) + 1 );
		}
		for( Edge _b : b.edges ){
			if( _b.isIncidentOn(a) )
			if( ! a.hasEdge(_e) )
				allEdges.add( 
		}
		*/
		
		g.removeEdge(e); // prune this edge
		
		// Newly joined vertex
		Vertex c = new Vertex( Math.min(a.id, b.id) );
		
		g.removeVertex(a); // get rid of nodes w/ their edges
		g.removeVertex(b);
		
		g.addVertex(c); // add lonely node w/ no edges yet
		
		//now add back edges avoiding self loops 
		for( Edge f : allEdges ){
			Vertex h = (f.src.equals(a))? c : (f.src.equals(b) )? c : f.src;
			Vertex t = (f.dst.equals(a))? c : (f.dst.equals(b) )? c : f.dst;
			if( ! h.equals(t) ){
				Edge f2 = new Edge(h, t);
				g.addEdge(f2);
			}
		}
	}
	
	public static Graph loadGraph(String file) throws IOException, URISyntaxException{
		File f = new File( MinimumCut.class.getResource(file).toURI() );
		return loadGraph( f );
	}
	public static Graph loadGraph(File f) throws IOException{
		//File f = new File( MinimumCutsTest.class.getResource(file).toURI() );
		System.out.print("Loading file " + f.getName() + "...");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = null;
		Graph g = new AdjacencyListGraph();
		int cnt = 0;
		while( (line=br.readLine())!=null){
			cnt++;
			String[] verts = line.trim().split("\\s+");
			Vertex a = new Vertex(Integer.parseInt(verts[0]));
			g.addVertex(a);
			for( int ii = 1; ii <verts.length; ii++){
				Vertex b = new Vertex(Integer.parseInt(verts[ii]));
				Edge e = new Edge( a, b );
				if( ! g.hasEdge(e) )
					g.addEdge( e );
			}
		}
		System.out.println(cnt + " total lines.");
		return g;
	}
	
}

