package com.mnasser.algos;

public class Percolation {
	
	private static class Node {
		boolean open = false;
		boolean full = false;
		Node parent = null;
	}
	
	private Node[][] grid;
	
	// create N-by-N grid, with all sites blocked
	public Percolation(int N){
		grid = new Node[N][N];
		for(int ii = 0; ii<N; ii++){
			for(int jj = 0; jj<N; jj++){
				grid[ii][jj] = new Node();
			}
		}
	}
	
	// open site (row i, column j) if it is not already
	public void open(int i, int j){
		grid[i][j].open = true;
	}
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j){
		return grid[i][j].open;
	}
	// is site (row i, column j) full?
	public boolean isFull(int i, int j){
		return grid[i][j].full;
	}
	// does the system percolate?
	public boolean percolates(){
		return false;
	}
}
