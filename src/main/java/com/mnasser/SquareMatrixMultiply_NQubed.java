package com.mnasser;

import java.util.Arrays;

public class SquareMatrixMultiply_NQubed{

	public static SquareMatrix squareMatrixMultiply(SquareMatrix A, SquareMatrix B){
		SquareMatrix C = new SquareMatrix(A.n);
		int[][] c_matrix = new int[A.n][A.n];
		for(int row=0;row<A.n; row++){
			for(int col=0;col<A.n;col++){
				int sum = 0;
				for(int kk =0; kk<C.n; kk++){
					sum += A.matrix[row][kk]  * B.matrix[kk][col];
				}
				c_matrix[row][col] = sum;
			}
		}
		C.setMatrix(c_matrix);
		return C;
	}
	
	static class SquareMatrix{
		private final int n;
		private int[][] matrix;
		public SquareMatrix(int n) {
			this.n = n;
		}
		public SquareMatrix(int[][] m){
			this.matrix = m;
			this.n = m.length;
		}
		int getSize(){return this.n;}
		void setMatrix(int[][] matrix){
			this.matrix = matrix;
		}
		int[] getCol(int colNum){
			int[] col = new int[n];
			for( int ii = 0; ii<n; ii++){
				col[ii] = matrix[ii][colNum];
			}
			return col;
		}
		int[] getRow(int rowNum){
			int[] row = new int[n];
			for( int ii = 0; ii<n; ii++){
				row[ii] = matrix[rowNum][ii];
			}
			return row;
		}
		static SquareMatrix makeRandomMatrix(int size){
			int[][] matrix = new int[size][size];
			// n^2 time to fill the random array
			for(int ii=0;ii<size;ii++){
				for(int jj=0;jj<size;jj++){
					matrix[ii][jj]=randomInt();
				}
			}
			return new SquareMatrix(matrix);
		}
		static int randomInt(int range){
			return (int)Math.round(Math.random()*range) * (((Math.floor(Math.random()*range)) % 2)==0? 1 : -1);
		}
		// makes random int between -100 to +100
		static int randomInt(){
			return randomInt(10);
		}
		private String _s = null;
		@Override
		public String toString() {
			if( _s != null) return _s;
			
			StringBuilder sb = new StringBuilder();
			for(int ii=0;ii<this.n;ii++){
				sb.append(Arrays.toString(this.matrix[ii]).replace("[", "").replace("]", ""))
				  .append('\n');
			}
			this._s = "["+sb.deleteCharAt(sb.length()-1).append(']').toString();
			return this._s;
		}
	}
	
	public static void main(String[] args) {
		int size = 2;
		SquareMatrix A = SquareMatrix.makeRandomMatrix(size);
		SquareMatrix B = SquareMatrix.makeRandomMatrix(size);
		System.out.println("A = \n"+A.toString());
		System.out.println("B = \n"+B.toString());
		
		SquareMatrix C = squareMatrixMultiply(A, B);
		System.out.println("C = \n"+C.toString());
	}
}
