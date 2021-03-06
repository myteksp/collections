package com.gf.parallel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface AsyncResult<T> {
    T get() throws InterruptedException, ExecutionException;
    T get(final long timeout) throws InterruptedException, TimeoutException, ExecutionException;
    boolean isCompleted();
    AsyncResult<T> onResult(final ResultListener<T> listener);
}
