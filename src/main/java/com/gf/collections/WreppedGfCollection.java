package com.gf.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.ToStringFunction;

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
	public T findFirst(FilterFunction<T> seeker) {
		return GfCollections.findFirst(this, seeker);
	}
	@Override
	public T findLast(FilterFunction<T> seeker) {
		return GfCollections.findLast(this, seeker);
	}
}
