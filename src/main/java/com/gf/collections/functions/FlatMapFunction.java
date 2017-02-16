package com.gf.collections.functions;

import java.util.List;

public interface FlatMapFunction<I, O> {
	List<O> flatMap(final I input);
}
