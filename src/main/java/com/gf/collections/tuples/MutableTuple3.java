package com.gf.collections.tuples;

import com.gf.collections.ArrayGfCollection;
import com.gf.collections.GfCollection;

public final class MutableTuple3<T1, T2, T3> implements Tuple{
	public T1 v1;
	public T2 v2;
	public T3 v3;
	
	public MutableTuple3(final T1 v1, final T2 v2, final T3 v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
	public MutableTuple3() {}
	
	public final Tuple3<T1, T2, T3> toUnmutable(){
		return new Tuple3<T1, T2, T3>(v1, v2, v3);
	}
	
	@Override
	public GfCollection<?> unpack() {
		final ArrayGfCollection<Object> res = new ArrayGfCollection<Object>(3);
		res.add(v1);
		res.add(v2);
		res.add(v3);
		return res;
	}
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		result = prime * result + ((v3 == null) ? 0 : v3.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		final Tuple3 other = (Tuple3) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		if (v3 == null) {
			if (other.v3 != null)
				return false;
		} else if (!v3.equals(other.v3))
			return false;
		return true;
	}
	@Override
	public final String toString() {
		return "MutableTuple3 [v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
	}
}
