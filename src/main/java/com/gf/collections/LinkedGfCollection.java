package com.gf.collections;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.gf.collections.functions.Action;
import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.Getter;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.ToStringFunction;
import com.gf.collections.iter.CollectionConsumer;
import com.gf.collections.iter.CollectionIterator;

public class LinkedGfCollection<T> extends LinkedList<T> implements GfCollection<T>{
	private static final long serialVersionUID = -9087483805324132145L;
	
	public LinkedGfCollection(final List<T> source){
		super(source);
	}
	public LinkedGfCollection(){
		super();
	}
	public LinkedGfCollection(final int initialSize){
		super();
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
	public <O> Map<O, GfCollection<T>> groupBy(Getter<T, O> getter) {
		return GfCollections.groupBy(this, getter);
	}
}
