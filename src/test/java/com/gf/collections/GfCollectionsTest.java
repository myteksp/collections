package com.gf.collections;

import org.junit.Test;

import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.Getter;
import com.gf.collections.functions.ToDouble;
import com.gf.collections.tuples.Tuple2;

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
	
	@Test
	public final void avarageCollectionsTest(){
		final double avarage = GfCollections.asLinkedCollection("1", "2", "3", "4").avarage(new ToDouble<String>() {
			@Override
			public final double toDouble(final String obj) {
				return Double.parseDouble(obj);
			}
		});
		assertEquals(0, Math.round(avarage - 2.5));
	}
	
	@Test
	public final void rangeCollectionsTest(){
		final GfCollection<Integer> coll = GfCollections.asArrayCollection(1, 2, 3, 4, 5, 6, 7).range(2, 3);
		assertEquals(3, coll.size());
		for (int i = 0; i < coll.size(); i++) {
			assertEquals(i + 3, (int)coll.get(i));
		}
	}
	
	@Test
	public final void splitCollectionsTest(){
		final GfCollection<Integer> coll = GfCollections.asArrayCollection(1, 2, 3, 4, 5, 6, 7, 8, 9).split(3).merge();
		for (int i = 0; i < coll.size(); i++) {
			assertEquals(i + 1, (int)coll.get(i));
		}
	}
	
	@Test
	public final void zipCollectionsTest(){
		final GfCollection<String> strs = GfCollections.asArrayCollection("1", "2", "3", "4", "5", "5", "3");
		final GfCollection<Integer> nums = GfCollections.asArrayCollection(1,2,3,4,5);
		final GfCollection<Tuple2<Integer, GfCollection<String>>> zipped = nums.zip(strs, new Getter<Integer, Integer>() {
			@Override
			public final Integer get(final Integer element) {
				return element;
			}
		}, new Getter<String, Integer>() {
			@Override
			public final Integer get(final String element) {
				return Integer.parseInt(element);
			}
		});
		assertEquals(Integer.valueOf(1), zipped.findFirst().v1);
		assertEquals("1", zipped.findFirst().v2.findFirst());
	}
}
