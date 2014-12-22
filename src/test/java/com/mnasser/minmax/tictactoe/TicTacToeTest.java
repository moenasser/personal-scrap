package com.mnasser.minmax.tictactoe;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.mnasser.minmax.tictactoe.TicTacToe.Board;
import com.mnasser.minmax.tictactoe.TicTacToe.Line;
import com.mnasser.minmax.tictactoe.TicTacToe.Position;

public class TicTacToeTest {

	private static Position p0 = new Position(0,0);
	private static Position p1 = new Position(1,1);
	private static Position p2 = new Position(2,2);
	
	@Test
	public void testPosition(){
		for( int ii = 0; ii < 3; ii++){
			for( int jj = 0; jj < 3; jj++) {
				Position p = new Position(ii,jj);
				//System.out.println( p + " " + p.hashCode());
				Assert.assertTrue( p.hashCode() >= 31);
			}
		}
		
//		Position p1 = new Position(1,1);
//		Position p2 = new Position(2,2);
		Assert.assertFalse( p1.equals(p2) );
		
		Assert.assertTrue( new Position(0,0).equals( new Position(0,0)) );
		Assert.assertTrue( new Position(1,1).equals( p1 ) );
		Assert.assertTrue( p1.equals( p1 ));
		Assert.assertTrue( p1.equals( new Position(1,1) ));
		Assert.assertEquals( p2, p2 );
		
		Assert.assertEquals( p1.hashCode(), new Position(1,1).hashCode());
		
		HashMap<Position, String> map = new HashMap<Position, String>();
		map.put(new Position(0,0), "(0,0)");
		
		Assert.assertTrue( map.containsKey(new Position(0,0)) );
		Assert.assertTrue( map.containsKey(p0) );
		
		for( Position p : map.keySet() ){
			Assert.assertEquals( p.toString(), map.get(p) );
			//System.out.println(p + " : " + p.hashCode()  + " val : " + map.get(p));
		}
		
		//System.out.println(map.get(new Position(0,0)).toString());
	}
	
	@Test
	public void testLine(){
		Line l = new Line();
		Assert.assertEquals(0, l.size());
		Assert.assertTrue( l.add(0, 0) ); // shouldn't be there yet
		Assert.assertEquals(1, l.size());
		
		
		
		Assert.assertFalse( l.add(0, 0)); // should be there already
		Assert.assertEquals(1, l.size());
		
	}
	
	@Ignore
	public void testBoard(){
		Board b = new Board();
		Assert.assertFalse( b.isGameWon() );
	}
}
