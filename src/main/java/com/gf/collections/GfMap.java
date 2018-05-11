package com.gf.collections;

import java.util.Map;

import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.MapFunction;

public interface GfMap<K, V> extends Map<K, V>{
	GfCollection<K> collectKeys();
	GfCollection<V> collectValues();
	GfCollection<Entry<K, V>> collectEntries();
	<R> GfCollection<R> mapKeys(final MapFunction<K, R> map);
	<R> GfCollection<R> mapValues(final MapFunction<V, R> map);
	<R> GfCollection<R> flatMapKeys(final FlatMapFunction<K, R> map);
	<R> GfCollection<R> flatMapValues(final FlatMapFunction<V, R> map);
}
