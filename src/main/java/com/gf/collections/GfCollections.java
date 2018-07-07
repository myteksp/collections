package com.gf.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

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

public final class GfCollections {	

	@SafeVarargs
	public static final <T> GfCollection<T> asCollection(final T ...elements){
		final GfCollection<T> result = new ArrayGfCollection<T>(elements.length);
		for(final T element : elements)
			result.add(element);

		return result;
	}

	public static final <T> GfCollection<T> asCollection(final Collection<T> collection){	
		final GfCollection<T> result = new LinkedGfCollection<T>();
		if (collection == null)
			return result;
		result.addAll(collection);	
		return result;
	}

	@SafeVarargs
	public static final <T> GfCollection<T> asLinkedCollection(final T ...elements){
		final GfCollection<T> result = new LinkedGfCollection<T>();
		for(final T element : elements)
			result.add(element);

		return result;
	}

	public static final <T> GfCollection<T> asLinkedCollection(final Collection<T> collection){
		final GfCollection<T> result = new LinkedGfCollection<T>();
		if (collection == null)
			return result;
		result.addAll(collection);
		return result;
	}

	@SafeVarargs
	public static final <T> GfCollection<T> asArrayCollection(final T ...elements){
		final GfCollection<T> result = new ArrayGfCollection<T>(elements.length);
		for(final T element : elements)
			result.add(element);

		return result;
	}

	public static final <T> GfCollection<T> wrapAsCollection(final List<T> list){
		if (list == null)
			return new LinkedGfCollection<T>();
		return new WreppedGfCollection<T>(list);
	}

	public static final <T> GfCollection<T> asArrayCollection(final Collection<T> collection){
		if (collection == null)
			return new ArrayGfCollection<T>();
		final GfCollection<T> result = new ArrayGfCollection<T>(collection.size());
		result.addAll(collection);
		return result;
	}

	public static final <T, O> GfMap<O, GfCollection<T>> groupBy(
			final GfCollection<T> input, 
			final Getter<T, O> getter) {
		final GfMap<O, GfCollection<T>> res = new GfHashMap<O, GfCollection<T>>(input.size());
		input.iterate(new CollectionConsumer<T>() {
			@Override
			public final void consume(final T element, final int index) {
				final O key = getter.get(element);
				GfCollection<T> col = res.get(key);
				if (col == null) {
					col = new LinkedGfCollection<T>();
					res.put(key, col);
				}
				col.add(element);
			}
		});
		return res;
	}

	public static final <T> GfCollection<T> find(final GfCollection<T> input, final FilterFunction<T> seeker){
		final GfCollection<T> result = new LinkedGfCollection<T>();
		CollectionIterator.iterate(input, new CollectionConsumer<T>() {
			@Override
			public final void consume(final T in, final int index) {
				if (seeker.filter(in))
					result.add(in);
			}
		});

		return result;
	}
	public static final <T> GfCollection<T> find(final GfCollection<T> input, final FilterFunction<T> seeker, final int limit){
		final GfCollection<T> result;
		if (input instanceof ArrayGfCollection){
			result = new ArrayGfCollection<T>(input.size());
		}else if (input instanceof LinkedGfCollection){
			result = new LinkedGfCollection<T>();
		}else if (input instanceof WreppedGfCollection){
			result = new LinkedGfCollection<T>();
		}else{
			throw new RuntimeException("Not supported collection type.");
		}
		try {
			CollectionIterator.iterate(input, new CollectionConsumer<T>() {
				@Override
				public final void consume(final T in, final int index) {
					if (seeker.filter(in)) {
						result.add(in);
						if (result.size() >= limit)
							throw new BreakException();
					}
				}
			});
		}catch(final BreakException e) {}

		return result;
	}

	private static final class BreakException extends RuntimeException{
		private static final long serialVersionUID = -7906960248612245072L;
	}

	public static final <T> T findFirst(final GfCollection<T> input){
		if (input.size() > 0)
			return input.get(0);

		return null;
	}

	public static final <T> T findLast(final GfCollection<T> input) {
		final int last = input.size() - 1;
		if (last < 0)
			return null;

		return input.get(last);
	}

	public static final <I, O> GfCollection<O> map(final GfCollection<I> input, final MapFunction<I, O> mapper){
		final GfCollection<O> result;
		if (input instanceof ArrayGfCollection){
			result = new ArrayGfCollection<O>(input.size());
			CollectionIterator.iterate(input, new CollectionConsumer<I>() {
				@Override
				public final void consume(final I in, final int index) {
					result.add(index, mapper.map(in));
				}
			});
		}else if (input instanceof LinkedGfCollection){
			result = new LinkedGfCollection<O>();
			CollectionIterator.iterate(input, new CollectionConsumer<I>() {
				@Override
				public final void consume(final I in, final int index) {
					result.add(mapper.map(in));
				}
			});
		}else if (input instanceof WreppedGfCollection){
			result = new LinkedGfCollection<O>();
			CollectionIterator.iterate(input, new CollectionConsumer<I>() {
				@Override
				public final void consume(final I in, final int index) {
					result.add(mapper.map(in));
				}
			});
		}else{
			throw new RuntimeException("Not supported collection type.");
		}

		return result;
	}

	private static final class WrappedInt{
		public int value;
		public WrappedInt() {
			this.value = 0;
		}
	}

	public static final <I, O> GfCollection<O> flatMap(final GfCollection<I> input, final FlatMapFunction<I, O> mapper){
		if (input instanceof ArrayGfCollection){
			final GfCollection<GfCollection<O>> results = new ArrayGfCollection<GfCollection<O>>(input.size());
			final WrappedInt len = new WrappedInt();
			CollectionIterator.iterate(input, new CollectionConsumer<I>() {
				@Override
				public final void consume(final I in, final int index) {
					final GfCollection<O> flat = wrapAsCollection(mapper.flatMap(in));
					len.value += flat.size();
					results.add(flat);
				}
			});
			final GfCollection<O> result = new ArrayGfCollection<O>(len.value);
			final CollectionConsumer<O> innerConsumer = new CollectionConsumer<O>() {
				@Override
				public final void consume(final O out, final int index) {
					result.add(out);
				}
			};
			CollectionIterator.iterate(results, new CollectionConsumer<GfCollection<O>>() {
				@Override
				public final void consume(final GfCollection<O> flat, final int index) {
					CollectionIterator.iterate(flat, innerConsumer);
				}
			});
			return result;
		}else if (input instanceof LinkedGfCollection){
			final GfCollection<O> result = new LinkedGfCollection<O>();
			for(final I inp : input)
				for(final O f : mapper.flatMap(inp))
					result.add(f);

			return result;
		}else if (input instanceof WreppedGfCollection){
			final GfCollection<GfCollection<O>> results = new ArrayGfCollection<GfCollection<O>>(input.size());
			final WrappedInt len = new WrappedInt();
			CollectionIterator.iterate(input, new CollectionConsumer<I>() {
				@Override
				public final void consume(final I in, final int index) {
					final GfCollection<O> flat = wrapAsCollection(mapper.flatMap(in));
					len.value += flat.size();
					results.add(flat);
				}
			});
			final GfCollection<O> result = new ArrayGfCollection<O>(len.value);
			final CollectionConsumer<O> innerConsumer = new CollectionConsumer<O>() {
				@Override
				public final void consume(final O out, final int index) {
					result.add(out);
				}
			};
			CollectionIterator.iterate(results, new CollectionConsumer<GfCollection<O>>() {
				@Override
				public final void consume(final GfCollection<O> flat, final int index) {
					CollectionIterator.iterate(flat, innerConsumer);
				}
			});
			return result;
		}else{
			throw new RuntimeException("Not supported collection type.");
		}
	}

	public static final <T> GfCollection<T> filter(final GfCollection<T> input, final FilterFunction<T> filter){
		final GfCollection<T> result;
		if (input instanceof ArrayGfCollection){
			result = new ArrayGfCollection<T>();
		}else if (input instanceof LinkedGfCollection){
			result = new LinkedGfCollection<T>();
		}else if (input instanceof WreppedGfCollection){
			result = new LinkedGfCollection<T>();
		}else{
			throw new RuntimeException("Not supported collection type.");
		}
		CollectionIterator.iterate(input, new CollectionConsumer<T>() {
			@Override
			public final void consume(final T in, final int index) {
				if (filter.filter(in))
					result.add(in);
			}
		});

		return result;
	}

	public static final <T> GfCollection<T> action(final GfCollection<T> input, final Action<T> act){
		act.onAction(input);
		return input;
	}

	public static final <T> String join(final GfCollection<T> input){
		return join(input, new ToString<T>() {
			@Override
			public final String getString(final T element) {
				if (element == null)
					return "null";
				return element.toString();
			}
		});
	}

	public static final <T> String join(final GfCollection<T> input, final ToString<T> stringifier){
		return join(input, stringifier, ",");
	}

	public static final <T> String join(final GfCollection<T> input, final ToString<T> stringifier, final String on){
		return join(input, stringifier, on, "", "");
	}

	public static final <T> String join(final GfCollection<T> input, final ToString<T> stringifier, final String on, final String prefix, final String suffix){
		final int length = input.size();
		switch(length){
		case 0:
			return prefix + suffix;
		case 1:
			return prefix + stringifier.getString(input.get(0)) + suffix;
		case 2:
			return prefix + stringifier.getString(input.get(0)) + on + stringifier.getString(input.get(1)) + suffix;
		case 3:
			return prefix + stringifier.getString(input.get(0)) + on + stringifier.getString(input.get(1)) + on + stringifier.getString(input.get(2)) + suffix;
		}
		final int lastIndex = length - 1;
		final StringBuilder sb = new StringBuilder(prefix.length() + suffix.length() + 40 + length * (on.length() + 1) * 40);
		sb.append(prefix);

		for (int i = 0; i < lastIndex; i++)
			sb.append(stringifier.getString(input.get(i))).append(on);

		sb.append(stringifier.getString(input.get(lastIndex)));

		sb.append(suffix);
		return sb.toString();
	}

	public static final <K, V> GfCollection<K> collectKeys(final GfHashMap<K, V> map){
		final Set<K> set = map.keySet();
		final int len = set.size();
		final ArrayGfCollection<K> res = new ArrayGfCollection<K>(len);
		for(final K k : set) 
			res.add(k);

		return res;
	}

	public static final <K, V> GfCollection<V> collectValues(final GfHashMap<K, V> map){
		final Collection<V> col = map.values();
		final int len = col.size();
		final ArrayGfCollection<V> res = new ArrayGfCollection<V>(len);
		for(final V v : col) 
			res.add(v);

		return res;
	}

	public static final <K, V> GfCollection<Entry<K, V>> collectEntries(final GfHashMap<K, V> map){
		final Set<Entry<K, V>> set = map.entrySet();
		final int len = set.size();
		final ArrayGfCollection<Entry<K, V>> res = new ArrayGfCollection<Entry<K, V>>(len);
		for(final Entry<K, V> e : set) 
			res.add(e);

		return res;
	}
	
	public static final <T> GfCollection<T> filterDuplicates(final GfCollection<T> src) {
		final int len = src.size();
		final HashMap<T, T> map = new HashMap<T, T>(len);
		final GfCollection<T> res = new ArrayGfCollection<T>(len);
		src.iterate(new CollectionConsumer<T>() {
			@Override
			public final void consume(final T element, final int index) {
				if (map.get(element) == null) {
					map.put(element, element);
					res.add(element);
				}
			}
		});
		return res;
	}
	
	public static final <T, O> GfCollection<T> filterDuplicates(final GfCollection<T> src, final Getter<T,O> getter) {
		final int len = src.size();
		final HashMap<O, T> map = new HashMap<O, T>(len);
		final GfCollection<T> res = new ArrayGfCollection<T>(len);
		src.iterate(new CollectionConsumer<T>() {
			@Override
			public final void consume(final T element, final int index) {
				final O key = getter.get(element);
				if (map.get(key) == null) {
					map.put(key, element);
					res.add(element);
				}
			}
		});
		return res;
	}

	public static final <T> T reduce(final GfCollection<T> src, final Reducer<T> reducer) {
		switch(src.size()) {
		case 0:
			return null;
		case 1:
			return src.get(0);
		default:
			final T first = src.get(0);
			src.iterate(new CollectionConsumer<T>() {
				private Reducer<T> r = new Reducer<T>() {
					@Override
					public final void reduce(final T result, final T element, final int index) {
						r = reducer;
					}
				};
				@Override
				public final void consume(final T element, final int index) {
					r.reduce(first, element, index);
				}
			});
			return first;
		}
	}

	public static final <T> GfCollection<T> takeFromStart(final GfCollection<T> input, final int n){
		final int size = input.size();
		if (n >= size)
			return input;

		final ArrayGfCollection<T> res = new ArrayGfCollection<T>(n);
		for (int i = 0; i < n; i++) 
			res.add(input.get(i));

		return res;
	}

	public static final <T> GfCollection<T> takeFromEnd(final GfCollection<T> input, final int n){
		final int size = input.size();
		if (n >= size)
			return input;

		final ArrayGfCollection<T> res = new ArrayGfCollection<T>(n);
		final int start = size-1;
		final int end = start - n;
		for (int i = start; i > end; i--) 
			res.add(input.get(i));

		return res;
	}

	private static final class RandomToken<T>{
		public final T element;
		public final int swapindex;
		public RandomToken(final T element, final int swapindex) {
			this.element = element;
			this.swapindex = swapindex;
		}
	}

	public static final <T> GfCollection<T> takeRandom(final GfCollection<T> input, final int n){
		final int size = input.size();
		switch(size) {
		case 0:
			return input;
		case 1:
			return populate(input.findFirst(), n);
		}
		final int range = n-1;
		final double d_range = range;
		final ArrayGfCollection<RandomToken<T>> res = new ArrayGfCollection<RandomToken<T>>(n);
		input.iterate(new CollectionConsumer<T>() {
			@Override
			public final void consume(final T element, final int index) {
				res.add(new RandomToken<T>(element, (int)Math.round(Math.random() * d_range)));
			}
		});
		if (n > size) {
			final double size_range = size - 1;
			final int toPopulate = n - size;
			for (int i = 0; i < toPopulate; i++) {
				final T e = input.get((int)Math.round(size_range * Math.random()));
				res.add(new RandomToken<T>(e, (int)Math.round(Math.random() * d_range)));
			}
		}
		res.iterate(new CollectionConsumer<GfCollections.RandomToken<T>>() {
			@Override
			public final void consume(final RandomToken<T> element, final int index) {
				final RandomToken<T> swap = res.get(element.swapindex);
				res.set(element.swapindex, element);
				res.set(index, swap);
			}
		});
		return res.take(n).map(new MapFunction<GfCollections.RandomToken<T>, T>() {
			@Override
			public final T map(final RandomToken<T> input) {
				return input.element;
			}
		});
	}

	public static final <T> GfCollection<T> populate(final T obj, final int n){
		final ArrayGfCollection<T> res = new ArrayGfCollection<T>(n);
		for (int i = 0; i < n; i++) 
			res.add(obj);
		return res;
	}
	public static final <T> T max(final GfCollection<T> coll, final ToDouble<T> val) {
		switch(coll.size()) {
		case 0:
			return null;
		case 1:
			return coll.findFirst();
		default:
			int index = 0;
			double value = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < coll.size(); i++) {
				final double c_val = val.toDouble(coll.get(i));
				if (c_val > value) {
					value = c_val;
					index = i;
				}
			}
			return coll.get(index);
		}
	}
	public static final <T> T min(final GfCollection<T> coll, final ToDouble<T> val) {
		switch(coll.size()) {
		case 0:
			return null;
		case 1:
			return coll.findFirst();
		default:
			int index = 0;
			double value = Double.POSITIVE_INFINITY;
			for (int i = 0; i < coll.size(); i++) {
				final double c_val = val.toDouble(coll.get(i));
				if (c_val < value) {
					value = c_val;
					index = i;
				}
			}
			return coll.get(index);
		}
	}
	public static final <T> GfCollection<T> top(final GfCollection<T> coll, final ToDouble<T> val, final int n){
		final int len = Math.abs(n);
		if (len < 2)
			return GfCollections.asLinkedCollection(max(coll, val));
		return coll.sortCollection(new Comparator<T>() {
			@Override
			public final int compare(final T a, final T b) {
				return Double.compare(val.toDouble(b), val.toDouble(a));
			}
		}).takeFromBegining(len);
	}
	public static final <T> GfCollection<T> buttom(final GfCollection<T> coll, final ToDouble<T> val, final int n){
		final int len = Math.abs(n);
		if (len < 2)
			return GfCollections.asLinkedCollection(min(coll, val));
		return coll.sortCollection(new Comparator<T>() {
			@Override
			public final int compare(final T a, final T b) {
				return Double.compare(val.toDouble(a), val.toDouble(b));
			}
		}).takeFromBegining(len);
	}
	public static final <T> GfCollection<T> append(final GfCollection<T> coll, final GfCollection<T> anotherCollection){
		if (anotherCollection == null)
			return coll;
		if (anotherCollection.isEmpty())
			return coll;
		if (anotherCollection.size() < (((double)coll.size()) * 0.25)) {
			coll.addAll(anotherCollection);
			return coll;
		}else {
			final ArrayGfCollection<T> res = new ArrayGfCollection<>(coll.size() + anotherCollection.size());
			final CollectionConsumer<T> consumer = new CollectionConsumer<T>() {
				@Override
				public final void consume(final T element, final int index) {
					res.add(element);
				}
			};
			coll.iterate(consumer);
			anotherCollection.iterate(consumer);
			return res;
		}
	}
	public static final <T> double avarage(final GfCollection<T> coll, final ToDouble<T> val) {
		if (coll.isEmpty())
			return 0;
		return coll.sum(val) / (double)coll.size();
	}
	
	public static final <T> int avarage(final GfCollection<T> coll, final ToInt<T> val) {
		if (coll.isEmpty())
			return 0;
		return coll.sum(val) / coll.size();
	}
	
	public static final <T> long avarage(final GfCollection<T> coll, final ToLong<T> val) {
		if (coll.isEmpty())
			return 0;
		return coll.sum(val) / (long)coll.size();
	}
	
	public static final <T> float avarage(final GfCollection<T> coll, final ToFloat<T> val) {
		if (coll.isEmpty())
			return 0;
		return coll.sum(val) / (float)coll.size();
	}

	public static final <T> double sum(final GfCollection<T> coll, final ToDouble<T> val) {
		double sum = 0;
		for(final Double v : coll.map(new MapFunction<T, Double>() {
			@Override
			public final Double map(final T input) {
				return val.toDouble(input);
			}
		})) 
			sum += v;
		
		
		return sum;
	}
	
	public static final <T> int sum(final GfCollection<T> coll, final ToInt<T> val) {
		int sum = 0;
		for(final Integer v : coll.map(new MapFunction<T, Integer>() {
			@Override
			public final Integer map(final T input) {
				return val.toInt(input);
			}
		})) 
			sum += v;
		
		
		return sum;
	}
	
	public static final <T> long sum(final GfCollection<T> coll, final ToLong<T> val) {
		long sum = 0;
		for(final Long v : coll.map(new MapFunction<T, Long>() {
			@Override
			public final Long map(final T input) {
				return val.toLong(input);
			}
		})) 
			sum += v;
		
		
		return sum;
	}
	
	public static final <T> float sum(final GfCollection<T> coll, final ToFloat<T> val) {
		float sum = 0;
		for(final Float v : coll.map(new MapFunction<T, Float>() {
			@Override
			public final Float map(final T input) {
				return val.toFloat(input);
			}
		})) 
			sum += v;
		
		
		return sum;
	}

	public static final <T> GfCollection<T> range(final GfCollection<T> coll, final int startIndex, final int length) {
		final ArrayGfCollection<T> result = new ArrayGfCollection<T>(length);
		CollectionIterator.iterate(coll, new CollectionConsumer<T>() {
			@Override
			public final void consume(final T element, final int index) {
				result.add(element);
			}
		}, startIndex, length);
		return result;
	}

	public static final <T> GfCollection<T> range(final GfCollection<T> coll, final CollectionConsumer<T> consumer, final int startIndex, final int length) {
		final ArrayGfCollection<T> result = new ArrayGfCollection<T>(length);
		CollectionIterator.iterate(coll, new CollectionConsumer<T>() {
			@Override
			public final void consume(final T element, final int index) {
				result.add(element);
				consumer.consume(element, index);
			}
		}, startIndex, length);
		return result;
	}

	public static final <T> GfCollection<GfCollection<T>> split(final GfCollection<T> coll, final int n) {
		final int len = Math.min(coll.size(), n);
		if (len < 2) {
			if (len == 1) {
				final ArrayGfCollection<GfCollection<T>> res = new ArrayGfCollection<GfCollection<T>>(1);
				res.add(coll);
				return res;
			}else {
				return GfCollections.asLinkedCollection();
			}
		}
		final int colLen = Math.max(10, (int)(Math.round((double)coll.size() / (double)len) + 1));
		final GfCollection<GfCollection<T>> result = new ArrayGfCollection<GfCollection<T>>(len);
		for (int i = 0; i < len; i++) 
			result.add(new ArrayGfCollection<T>(colLen));

		coll.iterate(new CollectionConsumer<T>() {
			private final int lastIndex = len - 1;
			@Override
			public final void consume(final T element, final int index) {
				result.get(index % lastIndex).add(element);
			}
		});
		return result;
	}

	public static final <T> GfCollection<T> merge(final GfCollection<GfCollection<T>> coll) {
		final int len = coll.size();
		if (len == 0)
			return asLinkedCollection();

		if (len == 1)
			return coll.findFirst();

		final int sumaryLen = (int)coll.sum(new ToInt<GfCollection<T>>() {
			@Override
			public final int toInt(final GfCollection<T> obj) {
				return obj.size();
			}
		});
		
		final ArrayGfCollection<T> result = new ArrayGfCollection<T>(sumaryLen);
		int colLevel = 0;
		for (;;) {
			for(final GfCollection<T> column : coll) 
				if (colLevel < column.size()) 
					result.add(column.get(colLevel));
			
			if (result.size() >= sumaryLen)
				break;
			
			colLevel++;
		}

		return result;
	}
}
