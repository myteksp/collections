package com.gf.collections.tuples;

public final class Tuple1 <T1> {
	public final T1 v1;
	
	public Tuple1(final T1 v1) {
		this.v1 = v1;
	}
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
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
		Tuple1 other = (Tuple1) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		return true;
	}
	@Override
	public final String toString() {
		return "Tuple1 [v1=" + v1 + "]";
	}
}