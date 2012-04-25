package com.mnasser.graph;

import java.util.ArrayList;
import java.util.List;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class MinimumCut {

	public static void main(String[] args) {
		Graph G = AdjacencyListGraph.makeRandomGraph(10);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		Edge e = chooseRandomEdge(G);
		System.out.println(e);
		G.removeEdge(e);
		System.out.println(G.toMatrixString());
		System.out.println(G);

		
		Vertex v = chooseRandomVertex(G);
		System.out.println(v);
		G.removeVertex(v);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		
		System.out.println("Contracting edge TEST ==========");
		G = AdjacencyListGraph.makeRandomGraph(10);
		e = chooseRandomEdge(G);
		System.out.println(G.toMatrixString());
		System.out.println(G);
		System.out.println("Contracting edge " + e +" ... ");
		
		contractEdge(G, e);
		
		System.out.println(G.toMatrixString());
		System.out.println(G);
		
		
		
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
	
}

