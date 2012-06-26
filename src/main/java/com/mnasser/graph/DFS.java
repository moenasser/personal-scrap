package com.mnasser.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Assert;

import com.mnasser.graph.Graph.Edge;
import com.mnasser.graph.Graph.Vertex;

public class DFS {

	public static void main(String[] args) {
		DirectedGraph g = new DirectedGraph();
		
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
		
		System.out.println(one.getOutBound());
		System.out.println(g.toString());
		
		System.out.println("=========TRAVERSING======");
		for( Vertex v : g.getVertices() ) {
			System.out.println("\nStarting at " + v);
			g.clearVisited();
			traverseDFS( v );
		}
	}
	
	Stack<Vertex> stack = new Stack<Vertex>();
	
	public static void traverseDFS(Vertex s ){
		traverseDFS(s, null, false, new RunningTotal());
	}
	public static void traverseDFS(Vertex s, FinishingOrder bth, boolean reverse, RunningTotal rt){
		s.visited = true;
		rt.inc();
		//System.out.println( "visiting " + s ); //s + ((p==null)?"":" (from "+p.id+")" ));
		//int v = 0;
		for( Edge e : (reverse)?s.getInBound():s.getOutBound() ){
			if( ! ((reverse)? e.src.isVisited() : e.dst.isVisited()) ) {
				traverseDFS( ((reverse)?e.src:e.dst), bth, reverse, rt );
				//v++;
			}
		}
		if( bth != null && s.order == -1 )  {
			bth.doBackTrack( s );
			//System.out.println("Backtracking. "+s.id+" is #"+ ((bth!=null)?s.order:"")+")");
		}
	}
	
	public interface BackTrackHook{
		public void doBackTrack(Vertex s);
	}
	public static class FinishingOrder implements BackTrackHook {
		private int _order = 0;
		private List<Integer> ordering = new ArrayList<Integer>(); // this might hurt
		public void doBackTrack(Vertex s){
			s.order = ++_order;
			ordering.add(s.id);
		}
		@Override
		public String toString() {
			return "Next order : " + _order;
		}
		int getCurrentOrder(){
			return _order;
		}
		int getMaxOrdered(){
			return ordering.get(ordering.size()-1);
		}
		List<Integer> getOrdering(){
			return this.ordering;
		}
	}
	public static class RunningTotal {
		private int size = 0;
		public void inc(){
			size ++;
		}
		int getSize(){return size;}
	}

}
