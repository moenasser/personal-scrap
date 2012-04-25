package com.mnasser.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class MinimumCut {

	public static void main(String[] args) throws IOException {
		Graph G = AdjacencyListGraph.makeRandomGraph(5);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		Edge e = chooseRandomEdge(G);
		System.out.println(e);
		G.removeEdge(e);
		System.out.println(G.toMatrixString());
		System.out.println(G);

		G.addEdge(e);
		G.addEdge(e);
		G.addEdge(e);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		Vertex v = chooseRandomVertex(G);
		System.out.println(v);
		G.removeVertex(v);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		
		System.out.println("Contracting edge TEST ==========");
		G = AdjacencyListGraph.makeRandomGraph(5);
		e = chooseRandomEdge(G);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		System.out.println("Contracting edge " + e +" ... ");
		
		contractEdge(G, e);
		
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		for( int ii =2; ii < G.getEdges().size(); ii++){
			System.out.println("==========  " + ii + " ==========");
			e = chooseRandomEdge(G);
			contractEdge(G, e);
			System.out.println("Contracting edge " + e);
			
			System.out.println(G.toMatrixString());
			System.out.println(G);
		}

		
		Graph g = AdjacencyListGraph.makeRandomGraph(8);
		System.out.println("============== Calling minimumCut() on");
		System.out.println(g.toMatrixString());
		System.out.println(g);
		findMinimumCut(g);
		

		Graph K = loadGraph("/home/mnasser/workspace/personal-scrap/src/main/resources/kargerAdj.txt");
		System.out.println("Karver graph = \n"+K.toMatrixString());
		//System.out.println(K);
		findMinimumCut(K);
	}
	
	
	static void findMinimumCut(Graph g){
		System.out.println("============== Finding minimum cut ");
		while( g.getVertices().size() > 2 ){
			Edge e = chooseRandomEdge(g);
			System.out.println("Contracting edge " + e);
			contractEdge(g, e);
			
			System.out.println(g.toMatrixString());
			System.out.println(g);
		}
		System.out.println("========\nMinimum Edges left = " + g.getEdges().size());
		System.out.println("========");
	}
	
	
	static Edge chooseRandomEdge(Graph g){
		int esize = g.getEdges().size();
		int idx = (int)(Math.random() * esize) % esize;
		return g.getEdges().get(idx);
	}
	static Vertex chooseRandomVertex(Graph g){
		int esize = g.getVertices().size();
		int idx = (int)(Math.random() * esize) % esize;
		return g.getVertices().get(idx);
	}
	
	static void contractEdge(Graph g, Edge e){
		if( ! g.hasEdge(e) ) throw new RuntimeException("Graph does not have edge "+e);
		// grab all other edges from vertex a and vertex b.
		// create a new vertex c with all the remaining edges of a & b
		// delete self loops.
		Vertex a = e.head; // hold references for later use
		Vertex b = e.tail;
		
		List<Edge> allEdges = new ArrayList<Edge>();
		allEdges.addAll(e.head.edges);
		allEdges.addAll(e.tail.edges);
		
		g.removeEdge(e); // prune this edge
		
		// New joined vertex
		Vertex c = new Vertex( Math.min(a.id, b.id) );
		
		g.removeVertex(a); // get rid of nodes w/ their edges
		g.removeVertex(b);
		
		g.addVertex(c); // add lonely node w/ no edges yet
		
		//now add back edges avoiding self loops 
		for( Edge f : allEdges ){
			Vertex h = (f.head == a)? c : (f.head == b )? c : f.head;
			Vertex t = (f.tail == a)? c : (f.tail == b )? c : f.tail;
			if( ! h.equals(t) ){
				Edge f2 = new Edge(h, t);
				g.addEdge(f2);
			}
		}
	}
	
	public static Graph loadGraph(String file) throws IOException{
		File f = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = null;
		Graph g = new AdjacencyListGraph();
		while( (line=br.readLine())!=null){
			String[] verts = line.trim().split("\\s+");
			Vertex a = new Vertex(Integer.parseInt(verts[0]));
			g.addVertex(a);
			for( int ii = 1; ii <verts.length; ii++){
				Vertex b = new Vertex(Integer.parseInt(verts[ii]));
				g.addEdge( new Edge( a, b ));
			}
		}
		return g;
	}
	
}

