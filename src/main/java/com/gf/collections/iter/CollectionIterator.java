package com.gf.collections.iter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;


import com.gf.collections.GfCollection;
import com.gf.collections.LinkedGfCollection;

public final class CollectionIterator {
	private static final <T> void iterate(
			final LinkedList<T> collection, 
			final CollectionConsumer<T> consumer){
		final Iterator<T> iter = collection.iterator();
		for (int i = 0; i < Integer.MAX_VALUE; i++) 
			try {
				consumer.consume(iter.next(), i);
			}catch(final NoSuchElementException ex) {
				return;
			}
	}
	@SuppressWarnings("unchecked")
	public static final <T> void iterate(
			final GfCollection<T> collection, 
			final CollectionConsumer<T> consumer) {
		if (consumer == null)
			return;
		if (collection == null)
			return;
		if (collection instanceof LinkedGfCollection)
			iterate((LinkedList<T>)collection, consumer);
		else 
			for (int i = 0; i < collection.size(); i++) 
				consumer.consume(collection.get(i), i);
	}
}
