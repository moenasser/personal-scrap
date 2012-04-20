package com.mnasser;

import java.util.Arrays;

public class MaxSubArray {

	public static Result findMaxCrossingSubarray(int[] A, int low, int mid, int hi){
		int max_left = -1,  max_right = -1;
		
		int leftSum = Integer.MIN_VALUE;
		int sum = 0;
		for( int ii = mid; ii >= low; ii--){
			sum += A[ii];
			if (sum > leftSum) {
				leftSum = sum;
				max_left = ii;
			}
		}
		
		int rightSum = Integer.MIN_VALUE;
		sum = 0;
		for( int ii = mid +1 ; ii <= hi; ii++){
			sum += A[ii];
			if (sum > rightSum){
				rightSum = sum;
				max_right = ii;
			}
		}
		return new Result( max_left, max_right, leftSum+rightSum);
	}
	
	public static Result findMaxSubarray(int[] A, int low, int hi){
		if ( hi == low ){
			//System.out.println("Singleton @ " + low + " : " + A[low]);
			return new Result(low, hi, A[low]);
		}
		int mid = (int)Math.floor( (low + hi)/2 );
		Result lower = findMaxSubarray(A, low, mid);
		Result upper = findMaxSubarray(A, mid + 1, hi);
		Result cross = findMaxCrossingSubarray(A, low, mid, hi);
		if( lower.sum >= upper.sum && lower.sum >= cross.sum){
			return lower;
		}
		if( upper.sum >= lower.sum && upper.sum >= cross.sum ){
			return upper;
		}
		return cross;
	}
	
	public static class Result{
		private int low, hi, sum = -1;
		public Result(int low, int hi, int sum) {
			this.low = low;
			this.hi = hi;
			this.sum = sum;
		}
		@Override
		public String toString() {
			return "["+ low + ".."+ hi+"] = " + sum;
		}
	}
	
	private static int getRandInt(){
		int r = (int)((Math.random() * 100) % 30);
		if ( (int)((Math.random()*10) % 2) == 0 ) r = r * -1;
		return r;
	}
	private static int[] makeRandIntArray(){
		int len = (int)((Math.random() * 100) % 25);
		int[] A = new int[len];
		System.out.println("len="+len);
		for( int ii = 0; ii < len; ii++){
			A[ii] = getRandInt();
		};
		return A;
	}
	public static void main(String[] args) {
		int[] A = makeRandIntArray();
		System.out.println(Arrays.toString(A));
		
		int mid = (int)(Math.floor(A.length/2));
		System.out.println("mid="+mid);
		System.out.println("Max Cross Array: " + findMaxCrossingSubarray(A, 0, mid, A.length-1));
		System.out.println("Max Array : " + findMaxSubarray(A, 0, A.length-1));
	}
}
