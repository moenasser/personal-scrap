package com.mnasser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSort {

	public static int getMedianOfThreePivot(int[] A, int start, int end){
		// looks at left, right and middle idx
		// returns the idx with the 'median' value of the three
		int leftIdx  = getLeftMostPivot(start, end);
		int midIdx   = getMiddlePivot(start, end);
		int rightIdx = getRightMostPivot(start, end);
		
		int left  = A[leftIdx];
		int mid   = A[midIdx];
		int right = A[rightIdx];
		
		System.out.println("A.length = "+(end-start+1)+"; A["+leftIdx+"]="+A[leftIdx]+"; A["+midIdx+"]="+A[midIdx]+"; A["+rightIdx+"]="+A[rightIdx]);
		
		if( mid >= left ){
			if ( mid <= right )
				return midIdx;
			if ( right >= left )
				return rightIdx;
			return leftIdx;
		}
		if ( mid >= right )
			return midIdx;
		if ( right >= left )
			return leftIdx;
		return rightIdx;
	}
	public static int getMiddlePivot(int start, int end){
		int size = end - start + 1;
		int mid = (int)Math.floor(size / 2);
		if( (end-start + 1) % 2 == 1 ) // odd
			mid += 1;
		return mid - 1; // arrays are 0-indexed
	}
	public static int getLeftMostPivot(int start, int end){
		return start;
	}
	public static int getRightMostPivot(int start, int end){
		return end;
	}
	public static int getRandomPivot(int start, int end){
		int size = end - start + 1;
		int randOffset = ((int)Math.floor(Math.random()*size)) % size;
		return start + randOffset;
	}
	protected enum PivotLocation {
		LEFT, RIGHT, 
		MEDIAN, RANDOM;
	}
	public static void swap(int[] A, int a, int b){
		if( a == b ) return;
		int c = A[a];
		A[a] = A[b];
		A[b] = c;
	}
	
	
	protected int partition(int[] A, int l, int r){
		int pivot = -1;//A[l];
		int _pivIdx = -1;
		switch(pivotLocation){
		case LEFT   :   pivot = A[l];  break;
		case MEDIAN : _pivIdx = getMedianOfThreePivot(A, l, r);  break; 
		case RIGHT  : _pivIdx = getRightMostPivot(l, r);break;
		case RANDOM : _pivIdx = getRandomPivot(l, r);   break;
		}
		if( _pivIdx > -1 ){
			pivot = A[_pivIdx];
			swap(A, l, _pivIdx);  // switch pivot so partition logic doesn't change
		}
		//elementsVisited++;// once for the pivot
		int i = l + 1;
		for( int j = l+1; j <= r; j++){
			//elementsVisited++;
			if ( A[j] < pivot ){
				swap(A, j, i);
				i++;
			}
		}
		swap( A, l, i-1);
		callsToPartition++;
		return i-1;
	}
	protected void quickSort(int[] A, int l, int r){
		if( l >= r ) return;
		int pivot = partition(A,l,r);
		// increment element count here as per HW #2's instructions
		elementsVisited +=  (r - l);  // m - 1 comparisons per call to partition for an array of size m
		quickSort(A, l, pivot - 1);
		quickSort(A, pivot + 1, r);
	}
	protected int callsToPartition = 0;
	protected int elementsVisited = 0;
	protected PivotLocation pivotLocation = PivotLocation.LEFT;
	
	public ResultCounts doQuickSort(int[] A, PivotLocation pl ){
		pivotLocation = pl;
		return doQuickSort(A);
	}
	public ResultCounts doQuickSort(int[] A){
		callsToPartition = 0;
		elementsVisited = 0;

		quickSort( A, 0, A.length - 1);
		
		assert testIsSorted(A);
		return new ResultCounts(A.length, callsToPartition, elementsVisited);
	}
	
	
	public static class ResultCounts {
		int n, callsToPartition, elementsVisited;
		long n_log_n, halfNsqaredMinusLogN;
		public ResultCounts(int n, int calls, int visits) {
			this.n = n;
			this.callsToPartition = calls;
			this.elementsVisited = visits;
			this.n_log_n = (long)(n * (Math.log(n)/Math.log(2)));
			this.halfNsqaredMinusLogN = (long)( (Math.pow(n, 2)/4) - (Math.log(n)/Math.log(2))) ;
		}
		@Override
		public String toString() {
			return "n = "+ n 
					+ ". Calls to partition = " + callsToPartition 
					+ ". Elements visisted = " + elementsVisited
					+ ". n*log(n) ~= " + n_log_n
					+ ". ((n^2)/2)-log(n) ~= " + halfNsqaredMinusLogN ;
		}
	}
	
	public static int[] makeRandomIntArray(int size){
		int[] A = new int[size];
		for( int ii = 0; ii < size; ii++){
			int tmp = (int)Math.round(Math.random()*100);
			tmp *= (((int)(Math.random()*100)) % 5 <= 1)? -1 : 1;   //randomly negate
			A[ii] = tmp;
		}
		return A;
	}
	public static boolean testIsSorted(int[] A){
		int curVal = Integer.MIN_VALUE; 
		for(int ii = 0; ii < A.length;ii++){
			if(ii==0){
				curVal=A[ii];
				continue;
			}
			if( curVal > A[ii] ) return false;
		}
		return true;
	}
	public static void runTests(int numRuns, PivotLocation pl){	
		runTests(numRuns, pl, false);
	}
	public static void runTests(int numRuns, PivotLocation pl, boolean preSort){
		QuickSort qs = new QuickSort();
		List<ResultCounts> rcs = new ArrayList<ResultCounts>();
		
		int N = 100;
		
		for(int ii = 0; ii < numRuns; ii++){
			int[] A = makeRandomIntArray( N );
			if ( preSort )
				qs.doQuickSort(A); // sort the array

			rcs.add( qs.doQuickSort( A , pl) );
		}
		
		System.out.println("n = " + N + ".  n*log(n) ~= " + rcs.get(0).n_log_n + ".  ((n^2)/2)-log(n) ~= " + rcs.get(0).halfNsqaredMinusLogN );
		System.out.println("Average Calls to Partition ["+pl+"]"+((preSort)?"[sorted]":"")+": "+ getAvgPartitionCalls(rcs));
		System.out.println("Average Elements Visited   ["+pl+"]"+((preSort)?"[sorted]":"")+": "+ getAvgComparisons(rcs));
		System.out.println("===================");
	}
	static double getAvgPartitionCalls(List<ResultCounts> rcs){
		int sum = 0;
		for( ResultCounts rc : rcs ){
			sum += rc.callsToPartition;
		}
		return sum / rcs.size();
	}
	static double getAvgComparisons(List<ResultCounts> rcs){
		int sum = 0;
		for( ResultCounts rc : rcs ){
			sum += rc.elementsVisited;
		}
		return sum / rcs.size();
	}
	public static void main(String[] args) throws IOException{
		/*
		QuickSort qs = new QuickSort();
		for(int ii = 0; ii < 10; ii++){
			int[]A = makeRandomIntArray(10);
			qs.doQuickSort(A);
		}
		int[]A = makeRandomIntArray(1000);
		qs.doQuickSort( A );
		
		A = makeRandomIntArray(100);
		qs.doQuickSort( A );
		
		
		qs.doQuickSort(makeRandomIntArray(20), PivotLocation.MEDIAN);
		qs.doQuickSort(makeRandomIntArray(20), PivotLocation.MEDIAN);
		
		qs.doQuickSort(makeRandomIntArray(20), PivotLocation.RANDOM);
		qs.doQuickSort(makeRandomIntArray(20), PivotLocation.RANDOM);
		
		qs.doQuickSort(A); // already sorted
		*/
		
		/*
		int testSize = 1000;
		runTests(testSize, PivotLocation.LEFT);
		runTests(testSize, PivotLocation.RIGHT);
		runTests(testSize, PivotLocation.RANDOM);
		runTests(testSize, PivotLocation.MEDIAN);
		         
		runTests(testSize, PivotLocation.LEFT, true);
		runTests(testSize, PivotLocation.RIGHT, true);
		runTests(testSize, PivotLocation.RANDOM, true);
		runTests(testSize, PivotLocation.MEDIAN, true);
		*/
		
		QuickSort qs = new QuickSort();
		int[] hwExample  = load( new File("/home/mnasser/workspace/personal-scrap/resources/QuickSort.txt"));
		int[] hwExample2 = Arrays.copyOf(hwExample, hwExample.length);
		int[] hwExample3 = Arrays.copyOf(hwExample, hwExample.length);
		int[] hwExample4 = Arrays.copyOf(hwExample, hwExample.length);
		
		assert (hwExample[getMedianOfThreePivot(hwExample, 0, hwExample.length -1 )] == 8260 );
		
		System.out.println("["+PivotLocation.LEFT+"]   "+ qs.doQuickSort( hwExample,  PivotLocation.LEFT) );
		System.out.println("["+PivotLocation.RIGHT+"]  "+ qs.doQuickSort( hwExample2, PivotLocation.RIGHT) );
		System.out.println("["+PivotLocation.RANDOM+"] "+ qs.doQuickSort( hwExample3, PivotLocation.RANDOM) );
		System.out.println("["+PivotLocation.MEDIAN+"] "+ qs.doQuickSort( hwExample4, PivotLocation.MEDIAN) );
		
		/*
		//Sorted
		System.out.println("Sorted :");
		System.out.println("["+PivotLocation.LEFT+"]   "+  qs.doQuickSort( hwExample,  PivotLocation.LEFT) );
		System.out.println("["+PivotLocation.RIGHT+"]  "+ qs.doQuickSort( hwExample2, PivotLocation.RIGHT) );
		System.out.println("["+PivotLocation.RANDOM+"] "+ qs.doQuickSort( hwExample3, PivotLocation.RANDOM) );
		System.out.println("["+PivotLocation.MEDIAN+"] "+ qs.doQuickSort( hwExample4, PivotLocation.MEDIAN) );
		*/
		
		/*
		int[] Test = new int[]{1,1,20,2,0,3,4,500};
		int idx = getMedianOfThreePivot(Test, 0, Test.length-1);
		System.out.println(Arrays.toString(Test));
		System.out.println("Test["+idx+"] = " + Test[idx]);
		*/
	}

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

}
