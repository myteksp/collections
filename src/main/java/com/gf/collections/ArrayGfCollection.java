package com.gf.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import com.gf.collections.functions.Action;
import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.Getter;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.Reducer;
import com.gf.collections.functions.ToNumber;
import com.gf.collections.functions.ToStringFunction;
import com.gf.collections.iter.CollectionConsumer;
import com.gf.collections.iter.CollectionIterator;

public class ArrayGfCollection<T> extends ArrayList<T> implements GfCollection<T>{
	private static final long serialVersionUID = 6247114635301644428L;
	
	public ArrayGfCollection(final List<T> source){
		super(source);
	}
	public ArrayGfCollection(){
		super();
	}
	public ArrayGfCollection(final int initialSize){
		super(initialSize);
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
	public GfCollection<T> filter(final FilterFunction<T> filter){
		return GfCollections.filter(this, filter);
	}
	@Override
	public String join(ToStringFunction<T> stringifier, String on, String prefix, String suffix) {
		return GfCollections.join(this, stringifier, on, prefix, suffix);
	}
	@Override
	public String join(ToStringFunction<T> stringifier, String on) {
		return GfCollections.join(this, stringifier, on);
	}
	@Override
	public String join(ToStringFunction<T> stringifier) {
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
	public T reduce(final Reducer<T> reducer) {
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
	public GfCollection<T> top(final int n, final ToNumber<T> value) {
		return GfCollections.top(this, value, n);
	}
	@Override
	public GfCollection<T> buttom(final int n, final ToNumber<T> value) {
		return GfCollections.buttom(this, value, n);
	}
	@Override
	public GfCollection<T> append(final GfCollection<T> collection) {
		return GfCollections.append(this, collection);
	}
}
