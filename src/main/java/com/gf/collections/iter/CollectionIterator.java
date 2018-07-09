package com.gf.collections.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;


import com.gf.collections.GfCollection;
import com.gf.collections.LinkedGfCollection;

public final class CollectionIterator {
	private static final <T> void iterateLinked(
			final GfCollection<T> collection, 
			final CollectionConsumer<T> consumer){
		int count = 0;
		for(final T elem : collection) 
			consumer.consume(elem, count++);
	}
	
	private static final <T> void doIterate(
			final GfCollection<T> collection, 
			final CollectionConsumer<T> consumer) {
		for (int i = 0; i < collection.size(); i++) 
			consumer.consume(collection.get(i), i);
	}
	
	public static final <T> void iterate(
			final GfCollection<T> collection, 
			final CollectionConsumer<T> consumer) {
		if (consumer == null)
			return;
		if (collection == null)
			return;
		if (collection instanceof LinkedGfCollection)
			iterateLinked(collection, consumer);
		else 
			doIterate(collection, consumer);
	}
	
	public static final <T> void iterate(
			final GfCollection<T> collection, 
			final CollectionConsumer<T> consumer, 
			final int start,
			final int lenght) {
		if (consumer == null)
			return;
		if (collection == null)
			return;
		final int size = collection.size();
		final int s = Math.min(Math.max(0, start), size - 1);
		final int l = Math.min(lenght, (size - s));
		if (s == 0 && l == size)
			iterate(collection, consumer);
		else
			if (collection instanceof LinkedGfCollection) {
				final int limit = s + l;
				final Iterator<T> iter = collection.iterator();
				for (int i = 0; i < Integer.MAX_VALUE; i++) 
					try {
						if (i < s)
							iter.next();
						else 
							if (i < limit)
								consumer.consume(iter.next(), i);
							else
								return;
					}catch(final NoSuchElementException ex) {
						return;
					}
			}else 
				for (int i = s; i < l + s; i++) 
					consumer.consume(collection.get(i), i);
	}
}
