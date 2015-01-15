package com.mnasser.util;

import org.junit.Assert;
import org.junit.Test;

public class HeapTest {

	@Test
	public void testHeap(){
		Heap<String> h = new HeapComparable<String>(false);
		
		h.insert("hi");
		h.insert("hello");
		h.insert("moe");
		h.insert("-");
		h.insert("go");
		h.insert("apple");
		h.insert("zebra");
		Heap.print(h);
		
		Assert.assertEquals( 7 , h.size() );
		
		
		String root = h.removeRoot();
		
		Assert.assertEquals("-", root);
		Assert.assertEquals( 6 , h.size() );
		
		root = h.removeRoot();
		
		Assert.assertEquals("apple", root);
		Assert.assertEquals( 5 , h.size() );
		
		h.insert("A-Team");
		
		Assert.assertEquals("-", root);
		Assert.assertEquals( 6 , h.size() );

		
		Heap.print(h);		
	}
}
