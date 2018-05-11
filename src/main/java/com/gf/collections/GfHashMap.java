package com.gf.collections;

import java.util.HashMap;

import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.MapFunction;

public final class GfHashMap<K, V> extends HashMap<K, V> implements GfMap<K, V>{
	private static final long serialVersionUID = 3009914229158727226L;
	
	public GfHashMap(final int initialCapacity, final float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	
	public GfHashMap(final int initialCapacity) {
		super(initialCapacity);
	}
	
	public GfHashMap() {
		super();
	}
	
	@Override
	public GfCollection<K> collectKeys() {
		return GfCollections.collectKeys(this);
	}

	@Override
	public GfCollection<V> collectValues() {
		return GfCollections.collectValues(this);
	}
	
	@Override
	public GfCollection<Entry<K, V>> collectEntries() {
		return GfCollections.collectEntries(this);
	}

	@Override
	public <R> GfCollection<R> mapKeys(MapFunction<K, R> map) {
		return collectKeys().map(map);
	}

	@Override
	public <R> GfCollection<R> mapValues(MapFunction<V, R> map) {
		return collectValues().map(map);
	}

	@Override
	public <R> GfCollection<R> flatMapKeys(FlatMapFunction<K, R> map) {
		return collectKeys().flatMap(map);
	}

	@Override
	public <R> GfCollection<R> flatMapValues(FlatMapFunction<V, R> map) {
		return collectValues().flatMap(map);
	}
}
