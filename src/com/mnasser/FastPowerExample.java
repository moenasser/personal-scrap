package com.mnasser;

import java.util.HashMap;
import java.util.Map;

public class FastPowerExample {
	public static long FastPower(long a, long b, Map<String,Integer> count){
		System.out.println("Got "+a +" ^ "+ b);
		count.put("running_total", count.get("running_total") + 1 );
		
		if ( b == 1 )
			return a;
		
		long c = a*a;
		//System.out.println("multipled "+a+" * "+a);
		
		long ans = FastPower(c, (long)Math.floor(b/2), count);
		
		if (b % 2 == 1) {
			//System.out.println("multipled ans "+ans+" * "+a);
			return a * ans;
		}
		return ans;
	}
	
	public static void test(long n, long p){
		HashMap<String,Integer> count = new HashMap<String,Integer>();
		count.put("running_total", 0);
		System.out.println(n+"^"+p+" = "+FastPower(n, p, count));
		System.out.println("Actual value is = " + Math.pow(n, p));
		System.out.println("Log("+p+") = "+(Math.log(p)/Math.log(2)) );
		System.out.println("Sqrt("+p+") = "+Math.sqrt(p) );
		System.out.println("Actual recurse calls = " + count.get("running_total"));
		System.out.println("=====");
	}
	public static void main(String[] args) {
		test(2,3);
		test(2,4);
		test(2,8);
		test(2,16);
		test(2,20);
		test(2,32);
		test(2,64);
		test(1,3);
		test(1,4);
		test(1,8);
		test(1,16);
		test(1,20);
		test(1,32);
		test(1,64);
		test(1,128);
		test(1,256);
		test(1,1024);
	}
}
