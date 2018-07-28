package com.gf.collections.presets;

import java.lang.reflect.Field;
import java.util.List;

import com.gf.collections.GfCollections;
import com.gf.collections.functions.FlatMapFunction;
import com.gf.collections.functions.ToDouble;

public final class Presets {
	public static final ToDouble<Object> toNumber = new ToDouble<Object>() {
		@Override
		public final double toDouble(final Object obj) {
			if (obj == null)
				return 0;
			
			if (obj instanceof Number) {
				final Number num = (Number)obj;
				return num.doubleValue();
			} else if (obj instanceof String){
				final String str = ((String)obj).trim();
				try {
					return Double.parseDouble(str);
				}catch(final Throwable t) {
					return 0;
				}
			} else {
				final Class<?> clz = obj.getClass();
				return GfCollections.asArrayCollection(clz.getFields())
				.flatMap(new FlatMapFunction<Field, Double>() {
					@Override
					public final List<Double> flatMap(final Field input) {
						try {
							final Object res = input.get(obj);
							if (res instanceof Number) {
								final Number num = (Number)res;
								return GfCollections.asArrayCollection(num.doubleValue());
							} else if (obj instanceof String){
								final String str = ((String)obj).trim();
								try {
									return GfCollections.asArrayCollection(Double.parseDouble(str));
								}catch(final Throwable t) {
									return GfCollections.asLinkedCollection();
								}
							}else {
								return GfCollections.asLinkedCollection();
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							return GfCollections.asLinkedCollection();
						}
					}
				}).avarage(new ToDouble<Double>() {
					@Override
					public final double toDouble(final Double obj) {
						return obj;
					}
				});
			}
		}
	};
}
