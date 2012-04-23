package com.mnasser;

import java.util.Arrays;


public class RandomizedSelect extends QuickSort {

	public RandomizedSelect() {
		pivotLocation = PivotLocation.RANDOM;
	}
	@Override
	protected final void quickSort(int[] A, int l, int r) {
		throw new RuntimeException("Trying to use Hidden & Unimplemented parent function!");
	}
	@Override
	public final ResultCounts doQuickSort(int[] A, PivotLocation pl) {
		throw new RuntimeException("Trying to use Hidden & Unimplemented parent function!");
	}

	
	protected int randomizedSelect(int[] A, int i, int l , int r) {
		if( l == r ) return A[l];
		
		int q = partition(A,l,r);
		elementsVisited +=  (r - l);  // m - 1 comparisons per call to partition for an array of size m
		int k = q - l + 1;
		System.out.println("k = " + k);
		if ( i == k ) // the pivot value is the answer
			return A[q];
		if ( i < k )
			return randomizedSelect(A, i, l, q-1);
		return randomizedSelect(A, i-k, q+1, r);
	}

	int doRandomizedSelect(int[] A, int i){
		elementsVisited = 0;
		callsToPartition = 0;
		return randomizedSelect(A, i, 0, A.length-1);
	}
	
	public static void main(String[] args) {
		int[] A = makeRandomIntArray(20);
		System.out.println(Arrays.toString(A));
		RandomizedSelect rs = new RandomizedSelect();
		System.out.println( rs.doRandomizedSelect(A, 12) );
		System.out.println(Arrays.toString(A));
	}
}
