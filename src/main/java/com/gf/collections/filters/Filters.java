package com.gf.collections.filters;

import java.util.Collection;

import com.gf.collections.functions.FilterFunction;

public final class Filters {
	public static final FilterFunction<Integer> isPrimeInt = new FilterFunction<Integer>() {
		@Override
		public final boolean filter(final Integer element) {
			final int n = Math.abs(element);
			switch(n) {
			case 0:
				return false;
			case 1:
				return false;
			case 2:
				return true;
			default:
				if (n % 2 == 0) return false;
				for(int i = 3; i * i <= n; i+=2) 
					if(n % i == 0)
						return false;
			}
			return true;
		}
	};
	public static final FilterFunction<Long> isPrimeLong = new FilterFunction<Long>() {
		@Override
		public final boolean filter(final Long element) {
			final long n = Math.abs(element);
			if (n == 0) return false;
			if (n == 1) return false;
			if (n == 2) return true;
			if (n % 2 == 0) return false;
			for(int i = 3; i * i <= n; i+=2) 
		        if(n % i == 0)
		            return false;
			return true;
		}
	};
	public static final FilterFunction<Object> isNotNull = new FilterFunction<Object>() {
		@Override
		public final boolean filter(final Object element) {
			return element != null;
		}
	};
	public static final FilterFunction<Object> isNotEmpty = new FilterFunction<Object>() {
		@Override
		public final boolean filter(final Object element) {
			if (element == null)
				return false;
			
			if (element instanceof Collection<?>) 
				return !((Collection<?>)element).isEmpty();
			else 
				return element.toString().trim().length() != 0;
			
		}
	};
}
