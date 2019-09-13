package com.gf.parallel;

public interface ResultListener<T>{
	void completed(final T result);
}
