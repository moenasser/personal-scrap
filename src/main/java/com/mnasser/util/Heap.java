package com.mnasser.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Generic, binary tree implementation of a heap.
 * 
 * The main point of a heap is to maintain that each parent element has 
 * children that are larger than it.  This means the root of the tree will obviously
 * have the smallest element. 
 * 
 * 2 basic functions are provided : insert & remove root
 * 
 * By default this Heap will use the given comparator to decide on the comparison 
 * ordering of the given elements in order to figure out what the "minimum" key value 
 * to return should be.
 * 
 * This Heap can be initialized with the boolean flag <code>returnMax</code> set to true
 * in order to use the given comparator to return maximums instead of minimums.
 * 
 * @author mnasser
 *
 */
public class Heap<K> implements Iterable<K>{
	
	protected Comparator<K> comp = null;
	private final boolean returnMax;
	
	// Returns minimum values using the given comparator to determine minimalness
	public Heap(Comparator<K> comparator){
		this( comparator , false ); // returns minimum by default
	}
	// Returns either minimum or maximum values using the given comparator.
	public Heap(Comparator<K> comparator, boolean returnMax){
		comp = comparator;
		this.returnMax = returnMax;
	}
	
//	/** Holder class to wrap keys and elements **/
//	public static final class Entry<K, E>{
//		public final K key;
//		public final E element;
//		public Entry(K k, E e){
//			key = k;
//			element = e;
//		}
//	}
	
	// our heap will be in array form. We'll use math to detect positions of 
	// children and parents
	private List<K> heap = new ArrayList<K>();
	
	private int maxIndex = 0; 
	
	public synchronized void insert(K key){ //, E element){
		//Entry<K,E> e = new Entry<K,E>(key, element);
		
		heap.add( maxIndex, key ); // insert at the end of the array ( last open leaf in the tree)
		
		bubbleUp( maxIndex ); // move it up the tree if need be
		
		maxIndex++; // increment our internal counter 
	}
	
	/**
	 * Recursively moves a leaf node up the tree until it has a parent that
	 * maintains the heap invariant
	 * @param current
	 * @param k
	 */
	private void bubbleUp(int current){
		if ( current == 0 ) return; // we started from the bottom now we here .. at the top
		
		// let's check if this current node is greater than its parent
		int parent = getParent(current);
		
		if ( needsSwap( heap.get( parent ) ,  heap.get( current ) ) ){
			swap( parent, current );
			bubbleUp( parent ); // since a swap was needed, lets check if we need more
		}
		
	}
	
	// swaps the elements at the two given locations
	private void swap( int left, int right){
		K swap = heap.set( left ,  heap.get( right ) ); // push second element into first spot
		heap.set( right, swap); // shove first element into second spot
	}
	
	/**
	 * If this child doesn't compare properly to its
	 * parent, then a swap is warranted. 
	 * 
	 * @param parent
	 * @param child
	 * @return Returns true if the parent is strictly less than 
	 * (not equal to or greater than) the child when this Heap is initialized to 
	 * returning minimums.  Returns the maximums when initialized to return maximums
	 */
	private boolean needsSwap( K parent, K child ){
		int res = compare( parent, child );
		res = returnMax ? res * -1  : res ;
		
		if( res <= 0 )
			return false;
		else
			return true;
	}
	
	/**
	 * 
	 * @param parent
	 * @param child
	 * @return
	 */
	protected int compare( K parent, K child ){
		return comp.compare(parent, child);
//		switch( comp.compare(parent, child)) {
//		case -1 :
//		case 0  :
//			return false;
//		default :
//			return true;
//		}
	}
	
	/**
	 * Removes the root element. 
	 * 
	 * Rebalances the tree.
	 * @return
	 */
	public synchronized K removeRoot(){
		if( heap.isEmpty() ) throw new RuntimeException("Heap is empty! Cannot remove anything.");
		
		K root = heap.get(0);  // save for later
		
		maxIndex--;
		K lastLeaf = heap.remove( maxIndex );
		heap.set( 0 , lastLeaf ); // massive promotion
		bubbleDown( 0 ); // push down until it rests at a location that maintains heap invariant
		
		return root;
	}
	
	/**
	 * Pushes the current element down the tree iff one of it's children
	 * does not maintain the heap invariant (that a parent is "less than" its children).
	 * 
	 * If this current element has children, and either of the children isn't greater 
	 * than it, then it will promote the smallest of its children and swap with it.
	 * @param index  Current 
	 * @param current
	 */
	private void bubbleDown(int current){
		K parent = heap.get(current);
		
		int child = getFirstChild( current );
		if( child >= maxIndex )	
			return; // no children. we are a leaf in the tree
		
		int sibling = child + 1 ; // does this parent have another child? ...
		
		if ( sibling >= maxIndex ) {
			// ...no, it has only 1 child. So check if it needs a swap and do so
			if ( needsSwap( parent, heap.get(child)) ){
				swap( current , child );
				
				bubbleDown( child ); // the parent has been pushed into the child's location
					// see if more push downs are required.
			}
		}
		else
		{
			// has 2 children.  Check if they are both smaller than the parent
			// swap with the smallest of the three
			boolean childSwap = needsSwap( parent, heap.get(child) );
			boolean siblingSwap = needsSwap( parent, heap.get(sibling) );
			
			int swapIdx = -1;
			if( childSwap ){
				if( siblingSwap){
					// both less than parent. Promote smallest 
					if( needsSwap( heap.get(child), heap.get(sibling)) ){
						// child is > sibling. Promote sibling as smallest
						swapIdx = sibling; 
					}
					else 
					{
						// child is small enough. 
						swapIdx = child;
					}
				}
				else{
					// swap with first child only
					swapIdx = child;
				}
			}
			else if( siblingSwap ){
				// swap with second child only
				swapIdx = sibling;
			}
			
			if( swapIdx > -1 ) {
				swap( current, swapIdx );
				
				bubbleDown( swapIdx );
			}
		}
	}

	/**Returns the number of elements in this heap*/
	public int size(){
		return heap.size();
	}
	
	@Override
	public Iterator<K> iterator() {
		return heap.iterator();
	}
	
	/*Returns the root element without removing it
	public K peek(){
		return  heap.isEmpty()? null :  heap.get(0);
	}
	*/
	
	/** Given index of a child, finds its parent **/
	public static int getParent(int child){
		return (int) Math.ceil( ((double) child) / 2.0 ) - 1;
	}
	/**
	 * Given index of a parent, finds its first (left most) child's index.
	 * The index of the second (right most) child is the left child index + 1. **/
	public static int getFirstChild(int parent){
		return (parent * 2) + 1; 
	}
	
	
	
	
	
	
	
	
	public static void print(Heap<? extends Comparable<?>> h){
		System.out.print("[");
		for( Comparable<?> c : h.heap ){
			System.out.print(c + ", ");
		}
		System.out.println("]");
	}
	
	public static void main(String[] args) {
		System.out.println( "0 -> " + Heap.getFirstChild(0));
		System.out.println( "0 -> " + (Heap.getFirstChild(0) + 1) );
		System.out.println( "1 -> " + Heap.getFirstChild(1));
		System.out.println( "1 -> " + (Heap.getFirstChild(1) + 1) );
		System.out.println( "2 -> " + Heap.getFirstChild(2));
		System.out.println( "2 -> " + (Heap.getFirstChild(2) + 1) );
		
		System.out.println( Heap.getParent(1) + " -> 1");
		System.out.println( Heap.getParent(2) + " -> 2");
		System.out.println( Heap.getParent(3) + " -> 3");
		System.out.println( Heap.getParent(4) + " -> 4");
		System.out.println( Heap.getParent(5) + " -> 5");
		System.out.println( Heap.getParent(6) + " -> 6");
		
		Heap<Integer> h = new Heap<Integer>(new Comparator<Integer>(){public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}}  ,   true );
		
		h.insert( 13 );
		h.insert( 13 );
		h.insert( 13 );
		h.insert( 13 );
		h.insert( 3 );
		h.insert( 5 );
		
		print(h);
		
		h.insert( 1 );
		print(h);
		
		h.insert( 2 );
		print(h);
		
		System.out.println( h.removeRoot() );
		print(h);
		
		System.out.println( h.removeRoot() );
		print(h);
		
		h.insert( 13 );
		print(h);
		
		System.out.println( h.removeRoot() );
		print(h);
		

		h.insert( 1 );
		print(h);
		
		System.out.println( h.removeRoot() );
		print(h);
		
	}
	
}
