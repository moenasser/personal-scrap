package com.mnasser;

public class DiceSum {

	public static int rollDice(){
		return ((int)Math.ceil(Math.random() * 6) % 6) + 1;
	}
	
	public static void main(String[] args) {
		for(int ii=0;ii<100;ii++){
			System.out.println(rollDice());
		}
		
		int sum = 0;
		for( int ii = 1; ii <= 6; ii++){
			for( int jj = 1; jj <= 6; jj++){
				sum += jj + ii;
			}
		}
		System.out.println("sum / outcomes = " + sum + " / " + 36 + "  =  " + ((double)sum/36));
	}
}
