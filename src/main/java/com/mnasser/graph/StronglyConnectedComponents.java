package com.mnasser.graph;

import com.mnasser.graph.DFS.FinishingOrder;
import com.mnasser.graph.Graph.Vertex;


public class StronglyConnectedComponents {

	public static void main(String[] args) {
		DirectedGraph g = new DirectedGraph();
		
		g.addEdge( new Vertex(1), new Vertex(7) );
		g.addEdge( new Vertex(7), new Vertex(4) );
		g.addEdge( new Vertex(4), new Vertex(1) );
		g.addEdge( new Vertex(7), new Vertex(9) );
		g.addEdge( new Vertex(9), new Vertex(6) );
		g.addEdge( new Vertex(6), new Vertex(3) );
		g.addEdge( new Vertex(3), new Vertex(9) );
		g.addEdge( new Vertex(6), new Vertex(8) );
		g.addEdge( new Vertex(8), new Vertex(2) );
		g.addEdge( new Vertex(2), new Vertex(5) );
		g.addEdge( new Vertex(5), new Vertex(8) );

		System.out.println(g);

		// First pass
		g.reverse();
		FinishingOrder fo = firstPass( g );
		
		g.clearVisited();
		g.unReverse();
		// Second Pass
	}
	
	public static FinishingOrder firstPass( DirectedGraph g){
		g.clearVisited();
		Vertex max = g.getMax();
		FinishingOrder fo = new FinishingOrder();
		for( int ii = max.id ; ii > 0; ii-- ){
			if(  g.hasVertex(ii) && ! g.getVertex(ii).isVisited())
				DFS.traverseDFS(g.getVertex(ii), fo, ((g.isRevered())? false : true) );
		}
		System.out.println(g);
		return fo;
	}
	
	public static void secondPass( DirectedGraph g, FinishingOrder fo ){
		Vertex leader = fo.getMaxOrdered();
		for( int ii = leader.id; ii > 0; ii-- ){
			
		}
	}
	
}
