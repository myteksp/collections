package com.gf.distributed;

import java.io.Closeable;

import com.gf.collections.GfCollection;

public interface Context extends Closeable{
	GfCollection<String> contextIds();
	
}
