package com.mnasser.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mnasser.graph.StronglyConnectedComponents.CountingMap;

public class SCC_SuperTest {
	
	public static DirectedGraph loadGraph(String file) throws IOException{
		
		File f = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(f), 1024*1024);
		String line = null;
		
		DirectedGraph g = new DirectedGraph();
		
		int cnt = 0;
		System.out.println("Loading directed graph ...");
		long start = System.currentTimeMillis();
		
		while( (line=br.readLine())!=null){
			String[] verts = new String(line).trim().split("\\s+");
			g.addEdge( Integer.parseInt(verts[0]) , Integer.parseInt(verts[1]) );
			cnt++;
			if( cnt % 100000 == 0 ){
				System.out.println("... " + cnt + " lines loaded ... ");
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("... done loading " + cnt + " edges");
		System.out.println("Loaded in " + (end-start)/1000 + "sec");
		return g;
	}
	
	public static void main(String[] args) throws IOException {
		DirectedGraph g = loadGraph("/home/mnasser/workspace/personal-scrap/src/main/resources/SCC.txt");
		System.out.println("Running SCC() ...");
		CountingMap cm = StronglyConnectedComponents.doSCC(g);
		System.out.println("Results : " + cm);
		
		List<Integer> sortedSet = new ArrayList<Integer>();
		sortedSet.addAll(cm.keySet());
		Collections.sort(sortedSet, Collections.reverseOrder());
		System.out.println("Sorted Results : "+sortedSet);
	}
}
