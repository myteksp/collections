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
	
	public static final <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final T5 v5){
		return new Tuple5<T1, T2, T3, T4, T5>(v1, v2, v3, v4, v5);
	}
	
	
	public static final <T1> MutableTuple1<T1> getMutable(final T1 v1){
		return new MutableTuple1<T1>(v1);
	}
	
	public static final <T1, T2> MutableTuple2<T1, T2> getMutable(final T1 v1, final T2 v2){
		return new MutableTuple2<T1, T2>(v1, v2);
	}
	
	public static final <T1, T2, T3> MutableTuple3<T1, T2, T3> getMutable(final T1 v1, final T2 v2, final T3 v3){
		return new MutableTuple3<T1, T2, T3>(v1, v2, v3);
	}
	
	public static final <T1, T2, T3, T4> MutableTuple4<T1, T2, T3, T4> getMutable(final T1 v1, final T2 v2, final T3 v3, final T4 v4){
		return new MutableTuple4<T1, T2, T3, T4>(v1, v2, v3, v4);
	}
	
	public static final <T1, T2, T3, T4, T5> MutableTuple5<T1, T2, T3, T4, T5> getMutable(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final T5 v5){
		return new MutableTuple5<T1, T2, T3, T4, T5>(v1, v2, v3, v4, v5);
	}
}
