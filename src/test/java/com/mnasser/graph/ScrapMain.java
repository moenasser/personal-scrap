package com.mnasser.graph;


public class ScrapMain {

	public static void main(String[] args) {
		double p = 0.001;
		double e = 0.0001; //epsilon 
		double mp = 1.0 - p;
		System.out.println("p = " + p +"  Fail rate is (1-p)  = " + mp);
		System.out.println("epsilon = " + e);
		double s = 1 - e;
		System.out.println("Need success rate of : " + s );
		
		double runs = (Math.log(e) / Math.log(mp));
		System.out.println("We'll need about "+ runs + " runs");
		
		runs = (Math.log(e) / Math.log(p));
		System.out.println("We'll need about "+ runs + " runs");
		
		
		double f = mp;
		int cnt = 0;
		do{
			f = f * mp;
			//System.out.println( f );
			cnt ++;
		}while( f > e );
		System.out.println( f );
		System.out.println(cnt);
		
	}
}
