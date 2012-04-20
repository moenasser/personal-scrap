package com.mnasser;

import java.util.Arrays;
import java.util.List;

public class QuickSort {

	public static int getMiddlePivot(int start, int end){
		int size = start - end + 1;
		return (int)Math.floor(size / 2);
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
	private enum PivotLocation {
		LEFT, //RIGHT, 
		MEDIAN, RANDOM;
	}
	public static void swap(int[] A, int a, int b){
		if( a == b ) return;
		int c = A[a];
		A[a] = A[b];
		A[b] = c;
	}
	
	
	private int partition(int[] A, int l, int r){
		int pivot = -1;//A[l];
		switch(pivotLocation){
		case LEFT : pivot = A[l]; break;
		case MEDIAN : pivot = getMiddlePivot(l, r); break;
		case RANDOM : pivot = getRandomPivot(l, r); break;
		}
		/*
		System.out.print("A["+l+".."+r+"]=[");
		System.out.print(A[l]+"..");
		System.out.print(A[r]+"] Pivot is = "); 
		System.out.print(pivot + " Array = "); 
		System.out.println(Arrays.toString(A));
		*/
		elementsVisited++;// once for the pivot
		int i = l + 1;
		for( int j = l+1; j <= r; j++){
			elementsVisited++;
			if ( A[j] < pivot ){
				swap(A, j, i);
				i++;
			}
		}
		swap( A, l, i-1);
		callsToPartition++;
		return i-1;
	}
	private void quickSort(int[] A, int l, int r){
		if( l >= r ) return;
		int pivot = partition(A,l,r);
		quickSort(A, l, pivot - 1);
		quickSort(A, pivot + 1, r);
	}
	private int callsToPartition = 0;
	private int elementsVisited = 0;
	private PivotLocation pivotLocation = PivotLocation.LEFT;
	
	public void doQuickSort(int[] A, PivotLocation pl ){
		pivotLocation = pl;
		doQuickSort(A);
	}
	public void doQuickSort(int[] A){
		callsToPartition = 0;
		elementsVisited = 0;
		System.out.println(Arrays.toString(A));

		quickSort( A, 0, A.length - 1);
		
		System.out.println(Arrays.toString(A));
		System.out.println("Test is sorted = " + testIsSorted(A));
		double n_log_n = A.length * (Math.log(A.length)/Math.log(2));
		System.out.println("n = "+ A.length 
				+ ". Calls to partition = " + callsToPartition 
				+ ". Elements visisted = " + elementsVisited
				+ ". n*log(n) = " + n_log_n
				+ ". (n^2)/4 = " + (long)(Math.pow(A.length, 2)/4) );
		System.out.println("==================");
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
	public static void main(String[] args) {
		//for( int ii=0; ii < 10 ; ii++) System.out.println(getRandomPivot(0, 100));
		
		QuickSort qs = new QuickSort();
		for(int ii = 0; ii < 10; ii++){
			int[]A = makeRandomIntArray(10);
			qs.doQuickSort(A);
		}
		int[]A = makeRandomIntArray(1000);
		qs.doQuickSort( A );
		
		A = makeRandomIntArray(100);
		qs.doQuickSort( A );
		
	}
}
