package com.gf.collections.tuples;

public final class Tuples {
	public static final <T1> Tuple1<T1> get(final T1 v1){
		return new Tuple1<T1>(v1);
	}
	
	public static final <T1, T2> Tuple2<T1, T2> get(final T1 v1, final T2 v2){
		return new Tuple2<T1, T2>(v1, v2);
	}
	
	public static final <T1, T2, T3> Tuple3<T1, T2, T3> get(final T1 v1, final T2 v2, final T3 v3){
		return new Tuple3<T1, T2, T3>(v1, v2, v3);
	}
	
	public static final <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(final T1 v1, final T2 v2, final T3 v3, final T4 v4){
		return new Tuple4<T1, T2, T3, T4>(v1, v2, v3, v4);
	}
}
