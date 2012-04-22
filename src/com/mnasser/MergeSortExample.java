package com.mnasser;

public class MergeSortExample {

	public void Merge(Object[] A, int p, int q, int r){
		int n1 = q - p + 1;
		int n2 = r - q;
		
		Object[] L = new Object[n1 + 1];
		Object[] R = new Object[n2 + 1];
		
		for (int ii = 0; ii < n1; ii++)	  L[ii] = A[p+ii-1];
		for (int jj = 0; jj < n2; jj++)   R[jj] = A[q+jj];
		
		L[n1+1] = null;
		R[n2+1] = null;
		
		int ii = 0;
		int jj = 0;
		
		for( int kk = 0; kk < r; kk++){
			
		}
	}
}
