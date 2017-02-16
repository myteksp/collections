package com.gf.collections;

import org.junit.Test;

import com.gf.collections.functions.FilterFunction;

import static org.junit.Assert.*;

import java.util.Arrays;

public final class GfCollectionsTest {
	@Test
	public final void sanityCollectionsTest(){
		assertEquals(Arrays.asList("2", "3"), GfCollections.asLinkedCollection("1", "2", "3", "4").filter(new FilterFunction<String>() {
			@Override
			public final boolean filter(final String element) {
				if (element.equalsIgnoreCase("2") || element.equalsIgnoreCase("3"))
					return true;
				return false;
			}
		}));
	}
}
