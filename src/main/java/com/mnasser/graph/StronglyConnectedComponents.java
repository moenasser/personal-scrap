package com.mnasser.graph;



import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import com.mnasser.graph.DFS.FinishingOrder;
import com.mnasser.graph.DFS.RunningTotal;
import com.mnasser.graph.Graph.Vertex;


public class StronglyConnectedComponents {

	public static void main(String[] args) {
		DirectedGraph g = new DirectedGraph();
		
		g.addEdge(  new Vertex(7) , new Vertex(1) );
		g.addEdge(  new Vertex(4) , new Vertex(7) );
		g.addEdge(  new Vertex(1) , new Vertex(4) );
		g.addEdge(  new Vertex(9) , new Vertex(7) );
		g.addEdge(  new Vertex(6) , new Vertex(9) );
		g.addEdge(  new Vertex(3) , new Vertex(6) );
		g.addEdge(  new Vertex(9) , new Vertex(3) );
		g.addEdge(  new Vertex(8) , new Vertex(6) );
		g.addEdge(  new Vertex(2) , new Vertex(8) );
		g.addEdge(  new Vertex(5) , new Vertex(2) );
		g.addEdge(  new Vertex(8) , new Vertex(5) );

		System.out.println(g);
		
		CountingMap cm = doSCC(g); 
		System.out.println(cm);
	}
	
	public static CountingMap doSCC(DirectedGraph g){
		// first pass
		FinishingOrder fo = firstPass( g );
		
		g.clearVisited();
		
		// Second Pass
		return secondPass(g, fo);
	}
	
	public static FinishingOrder firstPass( DirectedGraph g){
		g.clearVisited();
		Vertex max = g.getMax();
		FinishingOrder fo = new FinishingOrder();
		
		for( int ii = max.id ; ii > 0; ii-- ){
			if(  g.hasVertex(ii) && ! g.getVertex(ii).isVisited()){
				RunningTotal rt = new RunningTotal();
				DFS.traverseDFS(g.getVertex(ii), fo, true , rt);
				//System.out.println("Size = " + rt.getSize());
			}
		}
		//System.out.println(g);
		return fo;
	}
	
	public static CountingMap secondPass( DirectedGraph g, FinishingOrder fo ){
		g.clearVisited();
		List<Integer> ordering = fo.getOrdering();
		Collections.reverse(ordering); // reverse in place 
		
		CountingMap countingMap = new CountingMap();
		
		//for( int ii = ordering.size() - 1; ii >= 0; ii--){
		for( Integer ii : ordering ){
			Vertex leader = g.getVertex( ii );
			if( leader.isVisited() )
				continue;
			
			RunningTotal rt = new RunningTotal();
			DFS.traverseDFS( leader , null, false  , rt);
			countingMap.inc(rt.getSize());
		}
		return countingMap;
	}
	
	public static class CountingMap extends TreeMap<Integer,Integer>{
		public void inc(int size){
			if( ! this.containsKey(size) ){
				this.put(size, 1);
				return;
			}
			this.put(size, this.get(size) +1 );
		}
	}
	
}
