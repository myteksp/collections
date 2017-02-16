package com.gf.collections;

import java.util.List;

import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.ToStringFunction;

public interface GfCollection<T> extends List<T>{
	<O> GfCollection<O> map(final MapFunction<T, O> map);
	<O> GfCollection<O> flatMap(final FlatMapFunction<T, O> flatMap);
	GfCollection<T> filter(final FilterFunction<T> filter);
	String join(final ToStringFunction<T> stringifier, final String on, final String prefix, final String suffix);
	String join(final ToStringFunction<T> stringifier, final String on);
	String join(final ToStringFunction<T> stringifier);
	String join();
}
