package com.gf.parallel;

public interface ChainTask<I, O>{
	O run(final I input);
}