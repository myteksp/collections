package com.gf.collections;

import java.util.List;

import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.ToStringFunction;

public interface GfCollection<T> extends List<T>{
	public <O> GfCollection<O> map(final MapFunction<T, O> map);
	public <O> GfCollection<O> flatMap(final FlatMapFunction<T, O> flatMap);
	public GfCollection<T> filter(final FilterFunction<T> filter);
	public String join(final ToStringFunction<T> stringifier, final String on, final String prefix, final String suffix);
	public String join(final ToStringFunction<T> stringifier, final String on);
	public String join(final ToStringFunction<T> stringifier);
	public String join();
}
