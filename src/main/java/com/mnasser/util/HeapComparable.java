package com.mnasser.util;


/**
 * Version of <code>Heap</code> that does not need an explicit comparator
 * to order the given elements.  Instead this will use the natural ordering
 * of given elements that extend Comparable. (ex: String, Integer, Double, etc)
 * 
 * @author mnasser
 *
 * @param <K>
 */
public class HeapComparable<K extends Comparable<K>> extends Heap<K> {

	public HeapComparable() {	super(null);	}
	public HeapComparable(boolean returnMax) {	super(null, returnMax);	}

	
	/**
	 * If this child doesn't compare properly to its
	 * parent, then a swap is warranted. 
	 * 
	 * This swap check can use the natural ordering of the elements
	 * if this heap was not given an explicit comparator
	 * 
	 * @param parent
	 * @param child
	 * @return Returns true if the parent is strictly less than 
	 * (not equal to or greater than) the child
	 */
	protected int compare( K parent, K child ){
		return parent.compareTo(child);
	}
	
	
	
	public static void main(String[] args) {
		HeapComparable<String> h = new HeapComparable<String>(false);
		
		h.insert("hi");
		h.insert("hello");
		h.insert("moe");
		h.insert("-");
		h.insert("go");
		h.insert("apple");
		h.insert("zebra");
		
		Heap.print(h);
		
		h.removeRoot();
		
		Heap.print(h);
		
	}
}
