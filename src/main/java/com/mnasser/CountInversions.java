package com.mnasser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountInversions {

	public static int[] load(File f) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = null;
		List<Integer> lines = new ArrayList<Integer>();
		while( (line = br.readLine()) != null ){
			lines.add( Integer.parseInt(line));
		}
		int[] arr = new int[lines.size()];
		int idx = 0;
		for( int i : lines ){
			arr[idx] = i;
			idx ++;
		}
		return arr;
	}
	public static class SubArray{
		int low = -1, hi = -1;
		private final int size;
		private long inv = 0;
		public SubArray(int left, int right) {
			this.low = left;
			this.hi = right;
			this.size = right - left + 1;
		}
		public SubArray(int left, int right, long inversions) {
			this(left, right);
			this.inv = inversions;
		}
		@Override
		public String toString() {
			return "["+low+".."+hi+"] "+ size;
		}
	}
	public static SubArray countMergeSplitInversions(int[] A, SubArray left, SubArray right){
		// Left result subarray is sorted and so is the right subarray.
		// we'll sort the master array A in place of.
		int[] Aprime = new int[left.size + right.size];
		int aa = 0; // where we are in the new array
		int jj = 0; // pointer into right array 
		long inv = 0;
		for( int ii = 0; ii < left.size; ){  // pointer into left array
			if( jj >= right.size ){ // The case where we have exhausted the right side before the left.
				Aprime[aa] = A[left.low + ii ];
				ii++;
				aa++;
				continue;
			}
			if(A[left.low + ii] <= A[right.low + jj]){
				// so far so good... keep adding to new array
				Aprime[aa] = A[left.low + ii];
				ii ++;  // XXX : NOTE: ONLY increment the left pointer when we have actually added from it.
			}else{
				// Here we have a right value that is larger than the left.
				// count this inversion (the amount of elements remaining on the left side).
				inv += (left.size  - ii);
				Aprime[aa] = A[right.low + jj];
				jj ++ ; // increment the right array.
			}
			aa++; // with each occurrence we must keep incrementing the new array position  
		}
		// Finish off the new Array NOTE: could be done in the next step
		for( ; jj < right.size; jj++, aa++){
			Aprime[aa] = A[right.low + jj];
		}
		// Push everything back into A in the sorted order (so we don't pass around a lot of unnecessary arrays) 
		for( int ss = left.low; ss <= right.hi; ss ++){
			A[ss] = Aprime[ss - left.low];
		}
		//return inv;
		return new SubArray(left.low, right.hi, inv);
	}
	// Recursive call to divide and conquer array
	public static long countInversions(int[] A, SubArray sa){
		if( sa.size == 1 ){
			return 0;
		}
		int mid = (int)(Math.floor(sa.size / 2)) + sa.low - 1;
		long leftInversions  = countInversions( A, new SubArray(sa.low, mid) );
		long rightInversions = countInversions( A, new SubArray(mid + 1, sa.hi) );
		SubArray cross = countMergeSplitInversions( A, new SubArray(sa.low, mid) , new SubArray(mid + 1, sa.hi)); 
		return cross.inv + leftInversions + rightInversions;
	}
	public static long countInversions(int[] A, int left, int right){
		return countInversions(A, new SubArray(left, right));
	}
	
	public static void main(String[] args) throws IOException {
		String macDir = "/Users/mnasser/Documents/workspace/personal-scrap/src/main/resources/";
		String pcDir  = "/home/mnasser/Downloads/";
		String dir = (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0)? macDir : pcDir;

		int[] A = load( new File(dir + "CountingInversionsIntegerArray.txt") );
		System.out.println("Loaded A : " + A.length);
		System.out.println("["+A[0]+".."+A[A.length-1]+"]");
		
		int[] B = new int[]{ 7,6,5,4,3,2,1 };
		
		System.out.println("Array B : " + Arrays.toString(B));
		System.out.println("B has " + countInversions(B, 0, B.length-1) + " inversions");
		
		System.out.println("A has : " + countInversions(A , 0, A.length-1) + " inversions");
	}
}
