package com.gf.collections.functions;

public interface Reducer<T> {
	public void reduce(final T result, final T element, final int index);
}
