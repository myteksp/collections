package com.gf.collections.tuples;

public final class Tuple2 <T1, T2> {
	public final T1 v1;
	public final T2 v2;
	
	public Tuple2(final T1 v1, final T2 v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		final Tuple2 other = (Tuple2) obj;
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
		return true;
	}

	@Override
	public final String toString() {
		return "Tuple2 [v1=" + v1 + ", v2=" + v2 + "]";
	}
}
