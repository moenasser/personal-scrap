package com.mnasser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.TreeSet;

public class HashTable2Sum {

	public static void main(String[] args) throws IOException {
		TreeSet<Integer> tree = new TreeSet<Integer>();  
		
		BufferedReader br = new BufferedReader(new FileReader(new File("/home/mnasser/workspace/personal-scrap/src/main/resources/HashInt.txt")));
		String line = null;
		while( (line = br.readLine()) != null ){
			tree.add(Integer.parseInt(line));
		}
		System.out.println("Loaded " + tree.size() +" elements");
		
		
		String[] sums = "231552,234756,596873,648219,726312,981237,988331,1277361,1283379".split("\\,");
		BitSet bs = new BitSet(sums.length);
		int idx = 0;
		for( String sum : sums ){
			bs.set(idx ++ , two_sum(Integer.parseInt(sum), tree));
		}
		for( int ii = 0; ii < sums.length; ii++){
			System.out.print( (bs.get(ii))?"1":"0");
		}
		System.out.println();
	}
	
	public static boolean two_sum( final int sum, TreeSet<Integer> tree) {
		int sum1 = -1, sum2 = -1;
		boolean two_sum_possible = false;
		for( Integer s1 : tree ){
			if ( s1 >= sum ) break;
			if( tree.contains( sum - s1 ) ){
				two_sum_possible = true;
				sum1 = s1;
				sum2 = sum - s1;
				break;
			}
		}
		if( two_sum_possible )
			System.out.println("For sum " + sum + " values " + sum1 + " + " + sum2);
		else
			System.out.println("For sum " + sum + " not possile");
		
		return two_sum_possible;
	}
}
