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
import com.gf.collections.functions.ToDouble;
import com.gf.collections.functions.ToFloat;
import com.gf.collections.functions.ToInt;
import com.gf.collections.functions.ToLong;
import com.gf.collections.functions.ToString;
import com.gf.collections.iter.CollectionConsumer;
import com.gf.collections.iter.NotIndexedCollectionConsumer;
import com.gf.collections.tuples.Tuple2;

public interface GfCollection<T> extends List<T>{
	public <O> GfCollection<O> map(final MapFunction<T, O> map);
	public <O> GfCollection<O> flatMap(final FlatMapFunction<T, O> flatMap);
	public GfCollection<T> filter(final FilterFunction<T> filter);
	public String join(final ToString<T> stringifier, final String on, final String prefix, final String suffix);
	public String join(final ToString<T> stringifier, final String on);
	public String join(final ToString<T> stringifier);
	public String join(final String on);
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
	public GfCollection<T> iterate(final NotIndexedCollectionConsumer<T> consumer);
	public <O> GfMap<O, GfCollection<T>> groupBy(final Getter<T,O> getter);
	public T reduce(final Reducer<T> reducer);
	public GfCollection<T> take(final int n);
	public GfCollection<T> takeFromBegining(final int n);
	public GfCollection<T> takeFromEnd(final int n);
	public GfCollection<T> takeRandom(final int n);
	public T takeRandom();
	public GfCollection<T> top(final int n, final ToDouble<T> value);
	public GfCollection<T> buttom(final int n, final ToDouble<T> value);
	public GfCollection<T> top(final int n);
	public GfCollection<T> buttom(final int n);
	public GfCollection<T> append(final GfCollection<T> collection);
	public T max(final ToDouble<T> value);
	public T min(final ToDouble<T> value);
	public double avarage(final ToDouble<T> value);
	public int avarage(final ToInt<T> value);
	public long avarage(final ToLong<T> value);
	public float avarage(final ToFloat<T> value);
	public double avarage();
	public GfCollection<T> range(final int startIndex, final int length);
	public GfCollection<T> range(final CollectionConsumer<T> consumer, final int startIndex, final int length);
	public GfCollection<T> range(final NotIndexedCollectionConsumer<T> consumer, final int startIndex, final int length);
	public GfCollection<GfCollection<T>> split(final int n);
	public double sum(final ToDouble<T> value);
	public int sum(final ToInt<T> value);
	public long sum(final ToLong<T> value);
	public float sum(final ToFloat<T> value);
	public double sum();
	public T merge();
	public GfCollection<T> shufle();
	public <O> GfCollection<T> filterDuplicates(final Getter<T,O> getter);
	public GfCollection<T> filterDuplicates();
	public <M> GfCollection<Tuple2<T, M>> match(final Getter<T, M> loader);
	public <M, K> GfCollection<Tuple2<T, M>> match(final Getter<T, K> key, final Getter<K, M> loader);
	public <R, O> GfCollection<Tuple2<T, GfCollection<R>>> zip(
			final GfCollection<R> collection, final Getter<T, O> leftGetter, final Getter<R, O> rightGetter);
	public <R, O> GfCollection<Tuple2<T, R>> flatZip(
			final GfCollection<R> collection, final Getter<T, O> leftGetter, final Getter<R, O> rightGetter);
}