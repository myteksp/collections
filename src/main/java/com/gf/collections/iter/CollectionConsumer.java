package com.gf.collections.iter;

public interface CollectionConsumer<T> {
	void consume(final T element, final int index);
}
