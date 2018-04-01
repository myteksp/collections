package com.gf.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gf.collections.functions.Action;
import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.Getter;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.ToStringFunction;
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
	
	public static final <T, O> Map<O, GfCollection<T>> groupBy(
			final GfCollection<T> input, 
			final Getter<T, O> getter) {
		final Map<O, GfCollection<T>> res = new HashMap<O, GfCollection<T>>(input.size());
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
		return join(input, new ToStringFunction<T>() {
			@Override
			public final String getString(final T element) {
				if (element == null)
					return "null";
				return element.toString();
			}
		});
	}

	public static final <T> String join(final GfCollection<T> input, final ToStringFunction<T> stringifier){
		return join(input, stringifier, ",");
	}

	public static final <T> String join(final GfCollection<T> input, final ToStringFunction<T> stringifier, final String on){
		return join(input, stringifier, on, "", "");
	}

	public static final <T> String join(final GfCollection<T> input, final ToStringFunction<T> stringifier, final String on, final String prefix, final String suffix){
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
}
