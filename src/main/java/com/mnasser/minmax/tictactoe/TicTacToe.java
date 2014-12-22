package com.mnasser.minmax.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A game of Tic Tac Toe
// Two game modes :  
// Single human player vs Computer (min-max AI algorithm) 
// & Two human players 
public class TicTacToe {

	// values allowed on the playing board 
	enum Values {
		X, O, empty;
		public String toString(){
			switch(this){
			case X : return " X ";
			case O : return " O ";
			default: return "   ";
			}
		}
	}
	
	// A 3x3 tic tac toe Board.
	// Accepts moves and can print out current state of the board
	public static class Board {
		private Values[][] board = new Values[3][3];
		private Move previousMove;
		private Line diagUp = null;
		private Line diagDn = null;
		private int spotsFilled = 0;
		
		// inits an empty game board 
		Board() {
			previousMove = null;
			for( int xx = 0, lenx = board.length; xx < lenx; xx ++ ){
				for( int yy = 0, leny = board[xx].length; yy < leny; yy ++ ){
					board[xx][yy] = Values.empty;
 				}
			}
			diagDn = new Line(); diagUp = new Line();
			diagDn.add(0,0); diagDn.add(1,1); diagDn.add(2,2); // create \ diag
			diagUp.add(0,2); diagUp.add(1,1); diagUp.add(2,0); // create / diag
		}
		
		
		boolean spotAvailable(Move m){
			return board[m.x_idx][m.y_idx] == Values.empty;
		}
		
		// retrieves the value at the given coords
		Values get(int x, int y){
			return board[x][y];
		}
		Values get(Position p){
			return get(p.x, p.y);
		}
		
		// Adds a move to the board.
		// Sets an X or an O at the given position
		boolean addMove(Move m){
			if( ! spotAvailable(m) ) {
				System.out.println("Spot taken!");
				return false;
			}
			
			board[m.x_idx][m.y_idx] = m.val;
			previousMove = m;
			spotsFilled++;
			return true;
		}
		
		boolean isDraw(){
			return spotsFilled == 9;
		}
		
		// Prints to screen the board with current values 
		void drawBoard(){
			for( int xx = 0, lenx = board.length; xx < lenx; xx ++ ){
				for( int yy = 0, leny = board[xx].length; yy < leny; yy ++ ){
					System.out.print(board[xx][yy]);
					if( yy < board[xx].length - 1 ) 
						System.out.print("|");
				}
				System.out.println();
				if( xx < board.length -1 ) 
					System.out.print("-----------\n");
			}
		}
		
		// checks to see if the game has been won by the most recent player
		boolean isGameWon(){
			if( previousMove == null ) return false;
			//check column
			boolean done = true;
			for( int xx = 0, len = board[previousMove.y_idx].length; xx < len; xx ++ ){
				if( board[xx][previousMove.y_idx] != previousMove.val ){
					done = false;
					break;
				}
			}
			if( done ) return true;
			
			//check row
			done = true;
			for( int yy = 0, len = board[previousMove.x_idx].length; yy < len; yy ++ ){
				if( board[previousMove.x_idx][yy] != previousMove.val ){
					done = false;
					break;
				}
			}
			if( done ) return true;
			
			//check diagonals
			if( (previousMove.x_idx != 1 && previousMove.y_idx != 1) // corners
				|| 
				(previousMove.x_idx == 1 && previousMove.y_idx == 1) ) // center 
			{
				// first we'll check the top/left-to-bottom/right diag
				
				done = true;
				if( diagDn.contains(previousMove.x_idx, previousMove.y_idx) ){
					for( Position p : diagDn){
						if( get(p) != previousMove.val ) {
							done = false ;
							break;
						}
					}
					if( done ) return true;
				}
				done = true;
				if( diagUp.contains(previousMove.x_idx, previousMove.y_idx) ){
					for( Position p : diagUp){
						if( get(p) != previousMove.val ) {
							done = false;
							break;
						}
					}
					if( done ) return true;
				}
			}
			
			return false;
		}
	}
	
	// Represents a move on the board.
	// Coordinate and the value desired at that spot
	public static class Move {
		public final int x_idx;
		public final int y_idx;
		public final Values val;
		public Move(int x, int y, Values v){
			x_idx = x;
			y_idx = y;
			val = v;
		}
		public Move( Position p, Values v){
			x_idx = p.x;
			y_idx = p.y;
			val = v;
		}
		boolean equals(Position p){
			return (x_idx == p.x && y_idx == p.y);
		}
		boolean equals(Values v){
			return (val == v);
		}
	}
	// Represents a position on the board
	// simply an (x,y) coord
	public static class Position {
		private final int x;
		private final int y;
		public Position( int _x, int _y){
			this.x = _x;
			this.y = _y;
		}
		public int hashCode(){ return 31 + ((x + y)^2); }
		public boolean equals(Object p){ 
			if( p == null || ! (p instanceof Position) ) return false;
			Position other = (Position)p;
			return (this.x==other.x && this.y==other.y); 
		}
		public String toString(){ return "("+x+','+y+')' ;}
	}
	// A straight line of positions
	// a set that holds up to 3 Positions. Program must assure lineage.
	public static class Line extends HashSet<Position>{
		private static final long serialVersionUID = 1L;
		public boolean add(int x, int y){
			return this.add(new Position(x,y));
		}
		public boolean add(Position p){
			if( this.contains(p)) return false;
			if( this.size() == 3 )
				throw new RuntimeException("Can't add more than 3 positions on to this line!");
			return super.add(p);
		}
		public boolean contains(int x, int y){
			return this.contains(new Position(x,y));
		}
	}
		
	private Pattern moveInputRe = Pattern.compile("\\d\\,\\d");
	private Position getNextPosition( BufferedReader input ){
		String cmds;
		try {
			cmds = input.readLine().trim();
		} catch (IOException e) {
			throw new RuntimeException("Failure reading user's input!", e);
		}
		
		Matcher m = moveInputRe.matcher(cmds);
		if( ! m.matches() )
			return null;
		
		String[] x_y = cmds.split("\\,");
		int x = Integer.parseInt(x_y[0]);
		int y = Integer.parseInt(x_y[1]);
		return new Position( x, y );
	}
	
	protected void play(Board b, boolean twoPlayer) throws IOException{
		boolean player1turn = true; // current player's move
		
		BufferedReader input = new BufferedReader( new InputStreamReader( System.in)); 
		
		do {
			System.out.println("Player "+((player1turn)? "1" : "2" )+" Go > ");
			Move m = new Move( getNextPosition(input) ,  (player1turn)? Values.X : Values.O );
			if( b.addMove(m) )// if move allowed then switch to next player
				player1turn  = (player1turn)? false : true;
			
			b.drawBoard();
			
		} while ( ! b.isGameWon() && ! b.isDraw() );
		
		if( ! b.isDraw() ){
			// previous player had just finised turn
			System.out.println("PLAYER "+((player1turn)? "2" : "1" )+ " WON!"); 
		}
		else{
			System.out.println("SORRY! TIE GAME! NO WINNERS!");
		}
		
		input.close();
	}
	
	public static void main(String[] args) throws IOException {
		// main game loop goes here
		Board board = new Board();		
		board.drawBoard();
		
		TicTacToe ttt = new TicTacToe();
		ttt.play(board, true);
	}
}


