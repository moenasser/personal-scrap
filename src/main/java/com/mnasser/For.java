package com.mnasser;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class For {

	public static void main(String[] args) {
		String[] sarr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		
		for( String s : sarr){
			System.out.println(s);
		}
		
		for( String s : sarr){
//			For.each(sarr, new Loop(){
//				@Override
//				public void loopBody(Object o) {
//					System.out.println(o);
//				}
//			});
		}
	}
	
	
	private static ExecutorService ex = Executors.newFixedThreadPool(10);

	public static void each(Object[] arr, Callable c){
		for( Object o : arr){
			ex.submit( c );
		}
	}
	
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		ex.shutdown();
	}
	
}
