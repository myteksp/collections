package com.gf.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import com.gf.collections.iter.CollectionIterator;
import com.gf.collections.tuples.Tuple2;

public class WreppedGfCollection<T> implements GfCollection<T>{
	
	private final List<T> list;
	
	public WreppedGfCollection(final List<T> list){
		if (list == null)
			throw new NullPointerException("Can not wrap null as a collection.");
		
		this.list = list;
	}
	
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(T e) {
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public T get(int index) {
		return list.get(index);
	}

	@Override
	public T set(int index, T element) {
		return list.set(index, element);
	}

	@Override
	public void add(int index, T element) {
		list.add(index, element);
	}

	@Override
	public T remove(int index) {
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public <O> GfCollection<O> map(MapFunction<T, O> map) {
		return GfCollections.map(this, map);
	}

	@Override
	public <O> GfCollection<O> flatMap(FlatMapFunction<T, O> flatMap) {
		return GfCollections.flatMap(this, flatMap);
	}

	@Override
	public GfCollection<T> filter(FilterFunction<T> filter) {
		return GfCollections.filter(this, filter);
	}

	@Override
	public String join(ToString<T> stringifier, String on, String prefix, String suffix) {
		return GfCollections.join(this, stringifier, on, prefix, suffix);
	}

	@Override
	public String join(ToString<T> stringifier, String on) {
		return GfCollections.join(this, stringifier, on);
	}

	@Override
	public String join(ToString<T> stringifier) {
		return GfCollections.join(this, stringifier);
	}

	@Override
	public String join() {
		return GfCollections.join(this);
	}
	@Override
	public GfCollection<T> sortCollection(Comparator<T> comparator) {
		this.sort(comparator);
		return this;
	}
	@Override
	public GfCollection<T> find(FilterFunction<T> seeker) {
		return GfCollections.find(this, seeker);
	}
	@Override
	public GfCollection<T> find(FilterFunction<T> seeker, int limit) {
		return GfCollections.find(this, seeker, limit);
	}
	@Override
	public GfCollection<T> findFirst(Consumer<T> seeker) {
		final T res = GfCollections.findFirst(this);
		return res == null?GfCollections.asLinkedCollection():GfCollections.asArrayCollection(res);
	}
	@Override
	public GfCollection<T> findLast(Consumer<T> seeker) {
		final T res = GfCollections.findLast(this);
		return res == null?GfCollections.asLinkedCollection():GfCollections.asArrayCollection(res);
	}
	@Override
	public T findFirst() {
		return GfCollections.findFirst(this);
	}
	@Override
	public T findLast() {
		return GfCollections.findLast(this);
	}
	@Override
	public void forEach(Consumer<? super T> action) {
		CollectionIterator.iterate(this, new CollectionConsumer<T>() {
			@Override
			public final void consume(final T element, final int index) {
				action.accept(element);
			}
		});
	}

	@Override
	public GfCollection<T> action(Action<T> action) {
		return GfCollections.action(this, action);
	}
	@Override
	public GfCollection<T> iterate(CollectionConsumer<T> consumer) {
		CollectionIterator.iterate(this, consumer);
		return this;
	}
	@Override
	public <O> GfMap<O, GfCollection<T>> groupBy(Getter<T, O> getter) {
		return GfCollections.groupBy(this, getter);
	}
	@Override
	public T reduce(Reducer<T> reducer) {
		return GfCollections.reduce(this, reducer);
	}
	@Override
	public GfCollection<T> take(final int n) {
		return GfCollections.takeFromStart(this, n);
	}
	@Override
	public GfCollection<T> takeFromBegining(final int n) {
		return GfCollections.takeFromStart(this, n);
	}
	@Override
	public GfCollection<T> takeFromEnd(final int n) {
		return GfCollections.takeFromEnd(this, n);
	}
	@Override
	public GfCollection<T> takeRandom(final int n) {
		return GfCollections.takeRandom(this, n);
	}
	@Override
	public GfCollection<T> top(final int n, final ToDouble<T> value) {
		return GfCollections.top(this, value, n);
	}
	@Override
	public GfCollection<T> buttom(final int n, final ToDouble<T> value) {
		return GfCollections.buttom(this, value, n);
	}
	@Override
	public GfCollection<T> append(final GfCollection<T> collection) {
		return GfCollections.append(this, collection);
	}
	@Override
	public T max(final ToDouble<T> value) {
		return GfCollections.max(this, value);
	}
	@Override
	public T min(final ToDouble<T> value) {
		return GfCollections.min(this, value);
	}
	@Override
	public double avarage(final ToDouble<T> value) {
		return GfCollections.avarage(this, value);
	}
	@Override
	public GfCollection<T> range(int startIndex, int length) {
		return GfCollections.range(this, startIndex, length);
	}
	@Override
	public GfCollection<T> range(CollectionConsumer<T> consumer, int startIndex, int length) {
		return GfCollections.range(this, consumer, startIndex, length);
	}
	@Override
	public GfCollection<GfCollection<T>> split(final int n) {
		return GfCollections.split(this, n);
	}
	@Override
	public double sum(final ToDouble<T> value) {
		return GfCollections.sum(this, value);
	}
	@Override
	public int sum(final ToInt<T> value) {
		return GfCollections.sum(this, value);
	}
	@Override
	public long sum(final ToLong<T> value) {
		return GfCollections.sum(this, value);
	}
	@Override
	public float sum(final ToFloat<T> value) {
		return GfCollections.sum(this, value);
	}
	@Override
	public int avarage(final ToInt<T> value) {
		return GfCollections.avarage(this, value);
	}
	@Override
	public long avarage(final ToLong<T> value) {
		return GfCollections.avarage(this, value);
	}
	@Override
	public float avarage(final ToFloat<T> value) {
		return GfCollections.avarage(this, value);
	}
	@SuppressWarnings("unchecked")
	@Override
	public T merge() {
		return (T) GfCollections.merge((GfCollection<GfCollection<T>>) this);
	}
	@Override
	public <O> GfCollection<T> filterDuplicates(final Getter<T, O> getter) {
		return GfCollections.filterDuplicates(this, getter);
	}
	@Override
	public GfCollection<T> filterDuplicates() {
		return GfCollections.filterDuplicates(this);
	}
	@Override
	public String join(final String on) {
		return GfCollections.join(this, on);
	}
	@Override
	public <M> GfCollection<Tuple2<T, M>> match(final Getter<T, M> getter) {
		return GfCollections.match(this, getter);
	}
	@Override
	public <R, O> GfCollection<Tuple2<T, GfCollection<R>>> zip(
			final GfCollection<R> collection, 
			final Getter<T, O> leftGetter,
			final Getter<R, O> rightGetter) {
		return GfCollections.zip(this, collection, leftGetter, rightGetter);
	}
	@Override
	public <R, O> GfCollection<Tuple2<T, R>> flatZip(
			final GfCollection<R> collection, 
			final Getter<T, O> leftGetter,
			final Getter<R, O> rightGetter) {
		return GfCollections.flatZip(this, collection, leftGetter, rightGetter);
	}
	@Override
	public <M, K> GfCollection<Tuple2<T, M>> match(final Getter<T, K> key, final Getter<K, M> loader) {
		return GfCollections.match(this, key, loader);
	}
}
