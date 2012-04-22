package com.mnasser;

public class FibonacciConstantTime {

	private static final double SQRT5= Math.sqrt(5);
	public static final double GOLDEN_RATIO 			= (1.00 + SQRT5) / 2.00;
	public static final double GOLDEN_RATIO_CONJUGATE 	= (1.00 - SQRT5) / 2.00;

	static {
		System.out.println("GR: " + GOLDEN_RATIO);
		System.out.println("GRC: " + GOLDEN_RATIO_CONJUGATE);
		System.out.println("Sqrt(5) = " + SQRT5);
	}
	
	public static double fib(int seqNum){
		return Math.round( (Math.pow(GOLDEN_RATIO, seqNum) - Math.pow(GOLDEN_RATIO_CONJUGATE, seqNum) ) / SQRT5);
	}
	
	public static void main(String[] args) {
		for(int ii = 0; ii < 100; ii++){
			System.out.println("Fib("+ii+") = " + fib(ii));
		}
	}
}
