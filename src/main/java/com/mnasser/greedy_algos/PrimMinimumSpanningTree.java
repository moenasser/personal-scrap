package com.mnasser.greedy_algos;

import com.mnasser.graph.AdjacencyListGraph;
import com.mnasser.graph.Graph;
import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

/**
 * An implementation of Prim's minimum cost spanning tree.
 * Algo is greedy since it makes myopic, "best-at-that-time", decisions
 * when choosing best edges.
 * @author mnasser
 */
public class PrimMinimumSpanningTree {

	public static void main(String[] args) {
		Graph g = new AdjacencyListGraph(4);
		g.addEdge( new Edge(new Vertex(1), new Vertex(2), 1));
		g.addEdge( new Edge(new Vertex(2), new Vertex(3), 2));
		g.addEdge( new Edge(new Vertex(3), new Vertex(4), 5));
		g.addEdge( new Edge(new Vertex(4), new Vertex(1), 4));
		g.addEdge( new Edge(new Vertex(1), new Vertex(3), 3));
		
//		PriorityQueue<Map<String,Vertex>.Entry<String, Vertex>> heap = new PriorityQueue<String>();
		
	}
	
	public Graph MinimumlySpanTree(Graph g) {
		// pick random vertex
		Graph spanned = new AdjacencyListGraph();
		Vertex rand = g.getVertices().get( (int)(Math.random() * g.getVertexCount() % g.getVertexCount() )   );
		
		spanned.addVertex( new Vertex( rand.id ) );
		
		return null;
	}
	
	public Edge getMinimumCostEdge(Vertex v ){
		Edge n = null;
		for( Edge e : v.getEdges()){
			if( n == null || n.cost() > e.cost() )
				n = e;
		}
		return n;
	}
}
