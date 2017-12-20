package com.gf.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gf.collections.functions.FilterFunction;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.MapFunction;
import com.gf.collections.functions.ToStringFunction;

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
		return new WreppedGfCollection<T>(list);
	}

	public static final <T> GfCollection<T> asArrayCollection(final Collection<T> collection){
		final GfCollection<T> result = new ArrayGfCollection<T>(collection.size());
		result.addAll(collection);
		return result;
	}
	
	public static final <T> GfCollection<T> find(final GfCollection<T> input, final FilterFunction<T> seeker){
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
		for(final T in : input) 
			if (seeker.filter(in))
				result.add(in);
		
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
		for(final T in : input) 
			if (seeker.filter(in)) {
				result.add(in);
				if (result.size() >= limit)
					break;
			}
		
		return result;
	}
	
	public static final <T> T findFirst(final GfCollection<T> input, final FilterFunction<T> seeker){
		for(final T in : input) 
			if (seeker.filter(in))
				return in;
		
		return null;
	}
	
	public static final <T> T findLast(final GfCollection<T> input, final FilterFunction<T> seeker) {
		final int last = input.size() - 1;
		if (last < 0)
			return null;
		
		for (int i = last; i > -1; i--) {
			final T in = input.get(i);
			if (seeker.filter(in))
				return in;
		}
		return null;
	}

	public static final <I, O> GfCollection<O> map(final GfCollection<I> input, final MapFunction<I, O> mapper){
		final GfCollection<O> result;
		if (input instanceof ArrayGfCollection){
			result = new ArrayGfCollection<O>(input.size());
		}else if (input instanceof LinkedGfCollection){
			result = new LinkedGfCollection<O>();
		}else if (input instanceof WreppedGfCollection){
			result = new LinkedGfCollection<O>();
		}else{
			throw new RuntimeException("Not supported collection type.");
		}

		for(final I inp : input)
			result.add(mapper.map(inp));

		return result;
	}

	public static final <I, O> GfCollection<O> flatMap(final GfCollection<I> input, final FlatMapFunction<I, O> mapper){
		if (input instanceof ArrayGfCollection){
			final List<List<O>> results = new ArrayList<List<O>>(input.size());
			int len = 0;
			for(final I inp : input){
				final List<O> flat = mapper.flatMap(inp);
				len += flat.size();
				results.add(flat);
			}

			final GfCollection<O> result = new ArrayGfCollection<O>(len);

			for(final List<O> flat : results)
				for(final O out: flat)
					result.add(out);


			return result;
		}else if (input instanceof LinkedGfCollection){
			final GfCollection<O> result = new LinkedGfCollection<O>();
			for(final I inp : input)
				for(final O f : mapper.flatMap(inp))
					result.add(f);

			return result;
		}else if (input instanceof WreppedGfCollection){
			final GfCollection<O> result = new LinkedGfCollection<O>();
			for(final I inp : input)
				for(final O f : mapper.flatMap(inp))
					result.add(f);

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

		for(final T inp : input)
			if (filter.filter(inp))
				result.add(inp);

		return result;
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
