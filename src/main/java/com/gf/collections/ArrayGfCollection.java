package com.gf.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.ToStringFunction;

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
}
