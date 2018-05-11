package com.gf.collections;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import com.gf.collections.functions.Action;
import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.Getter;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.Reducer;
import com.gf.collections.functions.ToStringFunction;
import com.gf.collections.iter.CollectionConsumer;

public interface GfCollection<T> extends List<T>{
	public <O> GfCollection<O> map(final MapFunction<T, O> map);
	public <O> GfCollection<O> flatMap(final FlatMapFunction<T, O> flatMap);
	public GfCollection<T> filter(final FilterFunction<T> filter);
	public String join(final ToStringFunction<T> stringifier, final String on, final String prefix, final String suffix);
	public String join(final ToStringFunction<T> stringifier, final String on);
	public String join(final ToStringFunction<T> stringifier);
	public String join();
	public GfCollection<T> sortCollection(final Comparator<T> comparator);
	public GfCollection<T> find(final FilterFunction<T> seeker);
	public GfCollection<T> find(final FilterFunction<T> seeker, final int limit);
	public GfCollection<T> findFirst(final Consumer<T> consumer);
	public GfCollection<T> findLast(final Consumer<T> consumer);
	public T findFirst();
	public T findLast();
	public GfCollection<T> action(final Action<T> action);
	public GfCollection<T> iterate(final CollectionConsumer<T> consumer);
	public <O> GfMap<O, GfCollection<T>> groupBy(final Getter<T,O> getter);
	public T reduce(final Reducer<T> reducer);
}
