package com.gf.parallel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gf.collections.GfCollections;

public final class ParallelTest {
	@Test
	public final void scheduleTest() throws InterruptedException, TimeoutException, ExecutionException{
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, Parallel.newThreadFactory(ThreadPriority.MAX, "SC_TEST"));
		final ExecutorService executor = Parallel.newLimitedCachedExecutorService(3, Parallel.newThreadFactory(ThreadPriority.MAX, "TEST"), new LoggerDelegate() {
			@Override
			public final void log(final LogLevel logLevel, final String message, final String... params) {
				System.out.println(logLevel + " " + message + " " + GfCollections.asArrayCollection(params).join(", "));
			}
			@Override
			public final void log(final LogLevel logLevel, final String message, final Throwable cause) {
				System.out.println(logLevel + " " + message + " " + cause.toString());
			}
		});
		final long startTime = System.currentTimeMillis();
		final AsyncResult<String> f = Parallel.scheduleOn(()->"TEST", scheduler, executor, 1000, TimeUnit.MILLISECONDS);
		f.onResult(r->{
			final long eventualTime = System.currentTimeMillis() - startTime;
			System.out.println("GOT RESULT: " + r + " after " + eventualTime + " milliseconds.");
		});
		assertEquals("TEST", f.get(3000));
		shutdownThreadpols(scheduler, executor);
	}
	
	@Test
	public final void chainTest() throws InterruptedException, TimeoutException, ExecutionException {
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, Parallel.newThreadFactory(ThreadPriority.MAX, "SC_TEST"));
		final ExecutorService executor = Parallel.newLimitedCachedExecutorService(3, Parallel.newThreadFactory(ThreadPriority.MAX, "TEST"), new LoggerDelegate() {
			@Override
			public final void log(final LogLevel logLevel, final String message, final String... params) {
				System.out.println(logLevel + " " + message + " " + GfCollections.asArrayCollection(params).join(", "));
			}
			@Override
			public final void log(final LogLevel logLevel, final String message, final Throwable cause) {
				System.out.println(logLevel + " " + message + " " + cause.toString());
			}
		});
		final long startTime = System.currentTimeMillis();
		final AsyncResult<Integer> f = Parallel.scheduleChain(()->"INT_" + 1000, scheduler, executor, 1000, TimeUnit.MILLISECONDS)
		.execute(s->Integer.parseInt(s.split("_")[1]), executor)
		.execute(i->i+1000, executor)
		.result();
		f.onResult(r->{
			final long eventualTime = System.currentTimeMillis() - startTime;
			System.out.println("GOT RESULT: " + r + " after " + eventualTime + " milliseconds.");
		});
		assertEquals("2000", f.get(3000) + "");
		shutdownThreadpols(scheduler, executor);
	}
	
	private final void shutdownThreadpols(final ScheduledExecutorService scheduler, final ExecutorService executor) {
		scheduler.shutdownNow();
		executor.shutdownNow();
	}
}
