package com.gf.collections.tuples;

import com.gf.collections.ArrayGfCollection;
import com.gf.collections.GfCollection;

public final class Tuple5<T1, T2, T3, T4, T5> implements Tuple{
	public final T1 v1;
	public final T2 v2;
	public final T3 v3;
	public final T4 v4;
	public final T5 v5;
	
	public Tuple5(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final T5 v5) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
		this.v5 = v5;
	}
	
	public final MutableTuple5<T1, T2, T3, T4, T5> toMutable(){
		return new MutableTuple5<T1, T2, T3, T4, T5>(v1, v2, v3, v4, v5);
	}
	
	@Override
	public final GfCollection<?> unpack() {
		final ArrayGfCollection<Object> res = new ArrayGfCollection<Object>(5);
		res.add(v1);
		res.add(v2);
		res.add(v3);
		res.add(v4);
		res.add(v5);
		return res;
	}
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		result = prime * result + ((v3 == null) ? 0 : v3.hashCode());
		result = prime * result + ((v4 == null) ? 0 : v4.hashCode());
		result = prime * result + ((v5 == null) ? 0 : v5.hashCode());
		return result;
	}
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		final Tuple5 other = (Tuple5) obj;
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
		if (v4 == null) {
			if (other.v4 != null)
				return false;
		} else if (!v4.equals(other.v4))
			return false;
		if (v5 == null) {
			if (other.v5 != null)
				return false;
		} else if (!v5.equals(other.v5))
			return false;
		return true;
	}
	@Override
	public final String toString() {
		return "Tuple5 [v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + ", v4=" + v4 + ", v5=" + v5 + "]";
	}
}
