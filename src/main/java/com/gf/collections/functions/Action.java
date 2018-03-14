package com.gf.collections.functions;

import com.gf.collections.GfCollection;

public interface Action<T> {
	void onAction(final GfCollection<T> self);
}
