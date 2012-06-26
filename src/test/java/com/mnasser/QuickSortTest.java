package com.mnasser;

import org.junit.Assert;
import org.junit.Test;

public class QuickSortTest {

	@Test
	public void testMiddianValues() throws Exception {
		Assert.assertEquals( 0, QuickSort.getMiddlePivot(0, 1) );
		Assert.assertEquals( 1, QuickSort.getMiddlePivot(0, 2) );
		Assert.assertEquals( 1, QuickSort.getMiddlePivot(0, 3) );
		Assert.assertEquals( 2, QuickSort.getMiddlePivot(0, 4) );
		Assert.assertEquals( 2, QuickSort.getMiddlePivot(0, 5) );
		Assert.assertEquals( 3, QuickSort.getMiddlePivot(0, 6) );
		Assert.assertEquals( 3, QuickSort.getMiddlePivot(0, 7) );
		Assert.assertEquals( 4, QuickSort.getMiddlePivot(0, 8) );

		Assert.assertEquals( 1, QuickSort.getMiddlePivot(1, 1) );
		Assert.assertEquals( 1, QuickSort.getMiddlePivot(1, 2) );
		Assert.assertEquals( 2, QuickSort.getMiddlePivot(1, 3) );
		Assert.assertEquals( 2, QuickSort.getMiddlePivot(1, 4) );
		Assert.assertEquals( 3, QuickSort.getMiddlePivot(1, 5) );
		Assert.assertEquals( 3, QuickSort.getMiddlePivot(1, 6) );
		Assert.assertEquals( 4, QuickSort.getMiddlePivot(1, 7) );
		Assert.assertEquals( 4, QuickSort.getMiddlePivot(1, 8) );
	
	}
}
