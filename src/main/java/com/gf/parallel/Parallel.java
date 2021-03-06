package com.gf.parallel;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import com.gf.collections.GfCollections;

public final class Parallel {
	public static final void runOn(final Runnable task, final ExecutorService executor){
		if (task == null)
			throw new NullPointerException("Task can not be null.");
		if (executor == null)
			throw new NullPointerException("Executor can not be null.");
		executor.execute(task);
	}
	public static final void scheduleOn(
			final Runnable task, 
			final ScheduledExecutorService scheduler, final ExecutorService executor,
			final long delay, final TimeUnit timeUnit){
		if (task == null)
			throw new NullPointerException("Task can not be null.");
		if (scheduler == null)
			throw new NullPointerException("Scheduler can not be null.");
		if (executor == null)
			throw new NullPointerException("Executor can not be null.");
		if (timeUnit == null)
			throw new NullPointerException("TimeUnit can not be null.");
		scheduler.schedule(()->executor.execute(()->task.run()), delay, timeUnit);
	}
	public static final <T> AsyncResult<T> runOn(final Task<T> task, final ExecutorService executor){
		if (task == null)
			throw new NullPointerException("Task can not be null.");
		if (executor == null)
			throw new NullPointerException("Executor can not be null.");
		return toAsyncResult(CompletableFuture.supplyAsync(()->task.run(), executor));
	}
	public static final <T> void runOn(final Task<T> task, final ExecutorService executor, final ResultListener<T> listener){
		if (task == null)
			throw new NullPointerException("Task can not be null.");
		if (executor == null)
			throw new NullPointerException("Executor can not be null.");
		if (listener == null)
			throw new NullPointerException("Listener can not be null.");
		executor.execute(()->listener.completed(task.run()));
	}
	public static final <T> AsyncResult<T> scheduleOn(
			final Task<T> task, 
			final ScheduledExecutorService scheduler, final ExecutorService executor,
			final long delay, final TimeUnit timeUnit){
		if (task == null)
			throw new NullPointerException("Task can not be null.");
		if (scheduler == null)
			throw new NullPointerException("Scheduler can not be null.");
		if (executor == null)
			throw new NullPointerException("Executor can not be null.");
		if (timeUnit == null)
			throw new NullPointerException("TimeUnit can not be null.");
		final CompletableFuture<T> result = new CompletableFuture<T>();
		scheduler.schedule(()->executor.execute(()->result.complete(task.run())), delay, timeUnit);
		return toAsyncResult(result);
	}
	public static final <T> void scheduleOn(
			final Task<T> task, 
			final ScheduledExecutorService scheduler, final ExecutorService executor,
			final long delay, final TimeUnit timeUnit, final ResultListener<T> listener){
		if (task == null)
			throw new NullPointerException("Task can not be null.");
		if (scheduler == null)
			throw new NullPointerException("Scheduler can not be null.");
		if (executor == null)
			throw new NullPointerException("Executor can not be null.");
		if (timeUnit == null)
			throw new NullPointerException("TimeUnit can not be null.");
		if (listener == null)
			throw new NullPointerException("Listener can not be null.");
		scheduler.schedule(()->executor.execute(()->listener.completed(task.run())), delay, timeUnit);
	}
	public static final void scheduleAtFixedRateOn(
			final Runnable command, 
			final ScheduledExecutorService scheduler, final ExecutorService executor,
			final long initialDelay, final long period, final TimeUnit timeUnit){
		if (command == null)
			throw new NullPointerException("Command can not be null.");
		if (scheduler == null)
			throw new NullPointerException("Scheduler can not be null.");
		if (executor == null)
			throw new NullPointerException("Executor can not be null.");
		if (timeUnit == null)
			throw new NullPointerException("TimeUnit can not be null.");
		scheduler.scheduleAtFixedRate(()->executor.execute(command), initialDelay, period, timeUnit);
	}
	public static final <T>ExecutionChainBuilder<T> executeChain(final Task<T> task, final ExecutorService executor){
		return new ExecutionChainBuilder<T>(task, executor);
	}
	public static final <T>ExecutionChainBuilder<T> scheduleChain(final Task<T> task, final ScheduledExecutorService scheduler, 
			final ExecutorService executor, final long delay, final TimeUnit unit){
		return new ExecutionChainBuilder<T>(task, scheduler, executor, delay, unit);
	}
	public static final <T>ExecutionChainBuilder<T> chain(final AsyncResult<T> result){
		return new ExecutionChainBuilder<T>(result);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult(final AsyncResult<AsyncResult<T>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		cascade.onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult1(final AsyncResult<AsyncResult<AsyncResult<T>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult2(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult1(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult3(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult2(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult4(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult3(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult5(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult4(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult6(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult5(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult7(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult6(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult8(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>>>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult7(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult9(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>>>>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult8(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	public static final <T> AsyncResult<T> unwrapNestedResult10(final AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<AsyncResult<T>>>>>>>>>>>> cascade){
		final CompletableFuture<T> res = new CompletableFuture<T>();
		unwrapNestedResult9(cascade).onResult(fut->fut.onResult(r->res.complete(r)));
		return toAsyncResult(res);
	}
	//============UTIL METHODS============
	public static final class ExecutionChainBuilder<O> {
		private final AsyncResult<O> result;
		private ExecutionChainBuilder(final Task<O> task, final ExecutorService executor) {
			this.result = Parallel.runOn(task, executor);
		}
		private ExecutionChainBuilder(final Task<O> task, final ScheduledExecutorService scheduler, 
				final ExecutorService executor, final long delay, final TimeUnit unit) {
			this.result = Parallel.scheduleOn(task, scheduler, executor, delay, unit);
		}
		private ExecutionChainBuilder(final AsyncResult<O> task) {
			this.result = task;
		}
		public final <NO>ExecutionChainBuilder<NO> execute(final ChainTask<O, NO> task, final ExecutorService executor){
			final CompletableFuture<NO> res = new CompletableFuture<NO>();
			result.onResult(op->Parallel.runOn(()->res.complete(task.run(op)), executor));
			return new ExecutionChainBuilder<NO>(Parallel.toAsyncResult(res));
		}
		public final <NO>ExecutionChainBuilder<NO> schedule(
				final ChainTask<O, NO> task, 
				final ScheduledExecutorService scheduler, final ExecutorService executor,
				final long delay, final TimeUnit timeUnit){
			final CompletableFuture<NO> res = new CompletableFuture<NO>();
			result.onResult(op->Parallel.scheduleOn(()->res.complete(task.run(op)), scheduler, executor, delay, timeUnit));
			return new ExecutionChainBuilder<NO>(Parallel.toAsyncResult(res));
		}
		public final AsyncResult<O> result(){
			return result;
		}
		public final void result(final ResultListener<O> listener) {
			result.onResult(listener);
		}
	}
	
	private static final Runnable wrapRunnable(final Runnable runnable, final LoggerDelegate logger) {
		if (runnable == null)
			return null;
		final Runnable res = new Runnable() {
			private final LoggerDelegate log = logger;
			private final String id = UUID.randomUUID().toString();
			private final Runnable task = runnable;
			private final String startMessage(final long stratTime) {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("' start timestamp: ").append(stratTime).append("]");
				return sb.toString();
			}
			private final String endMessage(final long stratTime, final long endTime) {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("' start timestamp: ").append(stratTime).append(", ")
				.append("end timestamp: ").append(endTime).append(". Total time: ").append(endTime - stratTime).append(" milliseconds.]");
				return sb.toString();
			}
			private final String errorMessage(final long stratTime, final long endTime) {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("' ended with exception. Start timestamp: ").append(stratTime).append(", ")
				.append("end timestamp: ").append(endTime).append(". Total time: ").append(endTime - stratTime).append(" milliseconds.]");
				return sb.toString();
			}
			public final void run() {
				final long start = System.currentTimeMillis();
				log.log(LogLevel.INFO, startMessage(start));
				try {
					task.run();
				}catch(final Throwable t) {
					log.log(LogLevel.ERROR, errorMessage(start, System.currentTimeMillis()), t);
					return;
				}
				log.log(LogLevel.INFO, endMessage(start, System.currentTimeMillis()));
			}
			@Override
			public final String toString() {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("']");
				return sb.toString();
			}
		};
		logger.log(LogLevel.DEBUG, res.toString() + " submited.");
		return res;
	}
	
	private static final <T> Callable<T> wrapRunnable(final Callable<T> runnable, final LoggerDelegate logger) {
		if (runnable == null)
			return null;
		final Callable<T> res = new Callable<T>(){
			private final LoggerDelegate log = logger;
			private final String id = UUID.randomUUID().toString();
			private final Callable<T> task = runnable;
			private final String startMessage(final long stratTime) {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("' start timestamp: ").append(stratTime).append("]");
				return sb.toString();
			}
			private final String endMessage(final long stratTime, final long endTime) {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("' start timestamp: ").append(stratTime).append(", ")
				.append("end timestamp: ").append(endTime).append(". Total time: ").append(endTime - stratTime).append(" milliseconds.]");
				return sb.toString();
			}
			private final String errorMessage(final long stratTime, final long endTime) {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("' ended with exception. Start timestamp: ").append(stratTime).append(", ")
				.append("end timestamp: ").append(endTime).append(". Total time: ").append(endTime - stratTime).append(" milliseconds.]");
				return sb.toString();
			}
			@Override
			public final T call() throws Exception {
				final long start = System.currentTimeMillis();
				log.log(LogLevel.INFO, startMessage(start));
				final T result;
				try {
					result = task.call();
				}catch(final Exception t) {
					log.log(LogLevel.ERROR, errorMessage(start, System.currentTimeMillis()), t);
					throw t;
				}
				log.log(LogLevel.INFO, endMessage(start, System.currentTimeMillis()));
				return result;
			}
			@Override
			public final String toString() {
				final StringBuilder sb = new StringBuilder(100);
				sb.append("[Task '").append(id).append("']");
				return sb.toString();
			}
		};
		logger.log(LogLevel.DEBUG, res.toString() + " submited.");
		return res; 
	}
	
	public static final ExecutorService newLimitedCachedExecutorService(final int threadsCount, final ThreadFactory threadFactory, final LoggerDelegate logger) {
		final ExecutorService res = new ExecutorService() {
			private final LoggerDelegate log = logger;
			private final int threads = threadsCount;
			private final String threadsDescriptor = threadFactory.toString();
			private final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, threads,
					60L, TimeUnit.SECONDS,
					new SynchronousQueue<Runnable>(),
					threadFactory);
			@Override
			public final void execute(final Runnable command) {
				executor.execute(wrapRunnable(command, log));
			}
			@Override
			public final <T> Future<T> submit(final Runnable task, final T result) {
				return executor.submit(wrapRunnable(task, log), result);
			}
			@Override
			public final Future<?> submit(final Runnable task) {
				return executor.submit(wrapRunnable(task, log));
			}
			@Override
			public final <T> Future<T> submit(final Callable<T> task) {
				return executor.submit(wrapRunnable(task, log));
			}
			@Override
			public final List<Runnable> shutdownNow() {
				return executor.shutdownNow();
			}
			@Override
			public final void shutdown() {
				executor.shutdown();
			}
			@Override
			public final boolean isTerminated() {
				return executor.isTerminated();
			}
			@Override
			public final boolean isShutdown() {
				return executor.isShutdown();
			}
			@Override
			public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks, 
					final long timeout, 
					final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				return executor.invokeAny(GfCollections.asArrayCollection(tasks).map(t->wrapRunnable(t, log)), timeout, unit);
			}
			@Override
			public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
				return executor.invokeAny(GfCollections.asArrayCollection(tasks).map(t->wrapRunnable(t, log)));
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException {
				return executor.invokeAll(GfCollections.asArrayCollection(tasks).map(t->wrapRunnable(t, log)), timeout, unit);
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
				return executor.invokeAll(GfCollections.asArrayCollection(tasks).map(t->wrapRunnable(t, log)));
			}
			@Override
			public final boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
				return executor.awaitTermination(timeout, unit);
			}
			@Override
			public final String toString() {
				final StringBuilder sb = new StringBuilder(threadsDescriptor.length() + 50);
				sb.append("[Thread factory: '").append(threadsDescriptor).append("'. Thread amount limit: ").append(threads).append("]");
				return sb.toString();
			}
		};
		logger.log(LogLevel.DEBUG, "Logged executor created: " + res.toString());
		return res;
	}
	
	public static final ExecutorService newLimitedCachedExecutorService(final int threadsCount, final ThreadFactory threadFactory) {
		return new ExecutorService() {
			private final int threads = threadsCount;
			private final String threadsDescriptor = threadFactory.toString();
			private final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, threads,
					60L, TimeUnit.SECONDS,
					new SynchronousQueue<Runnable>(),
					threadFactory);
			@Override
			public final void execute(final Runnable command) {
				executor.execute(command);
			}
			@Override
			public final <T> Future<T> submit(final Runnable task, final T result) {
				return executor.submit(task, result);
			}
			@Override
			public final Future<?> submit(final Runnable task) {
				return executor.submit(task);
			}
			@Override
			public final <T> Future<T> submit(final Callable<T> task) {
				return executor.submit(task);
			}
			@Override
			public final List<Runnable> shutdownNow() {
				return executor.shutdownNow();
			}
			@Override
			public final void shutdown() {
				executor.shutdown();
			}
			@Override
			public final boolean isTerminated() {
				return executor.isTerminated();
			}
			@Override
			public final boolean isShutdown() {
				return executor.isShutdown();
			}
			@Override
			public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks, 
					final long timeout, 
					final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				return executor.invokeAny(tasks, timeout, unit);
			}
			@Override
			public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
				return executor.invokeAny(tasks);
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException {
				return executor.invokeAll(tasks, timeout, unit);
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
				return executor.invokeAll(tasks);
			}
			@Override
			public final boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
				return executor.awaitTermination(timeout, unit);
			}
			@Override
			public final String toString() {
				final StringBuilder sb = new StringBuilder(threadsDescriptor.length() + 50);
				sb.append("[Thread factory: '").append(threadsDescriptor).append("'. Thread amount limit: ").append(threads).append("]");
				return sb.toString();
			}
		};
	}
	public static final ThreadFactory newThreadFactory(final ThreadPriority priority, final String threadNamePrefix) {
		return new ThreadFactory() {
			private final ThreadPriority pr = priority;
			private final String prefix = threadNamePrefix;
			private final int p = convertPriority(pr);
			@Override
			public final Thread newThread(final Runnable r) {
				final Thread t = new Thread(r, getThreadName(prefix));
				t.setPriority(p);
				return t;
			}
			@Override
			public final String toString() {
				final StringBuilder sb = new StringBuilder(threadNamePrefix.length() + 40);
				sb.append("[Thread name prefix:'").append(prefix).append("', priority:'").append(pr).append("']");
				return sb.toString();
			}
		};
	}
	private static final AtomicLong threadsCounter = new AtomicLong(Long.MIN_VALUE + 10);
	public static final String getThreadName(final String prefix){
		return new StringBuilder(40 + prefix.length())
				.append(prefix)
				.append('-')
				.append(threadsCounter.incrementAndGet())
				.toString();
	}
	public static final int convertPriority(final ThreadPriority priority) {
		if (priority == null)
			throw new NullPointerException("priority can not be null.");
		switch (priority) {
		case MAX:
			return Thread.MAX_PRIORITY;
		case NORMAL:
			return Thread.NORM_PRIORITY;
		case MIN:
			return Thread.MIN_PRIORITY;
		default:
			return Thread.NORM_PRIORITY;
		}
	}
	public static final <T>AsyncResult<T> toAsyncResult(final CompletableFuture<T> future){
		if (future == null)
			throw new NullPointerException("future can not be null.");
		return new WrappedFuture<T>(future);
	}
	private static final class WrappedFuture<T> implements AsyncResult<T>{
		private final CompletableFuture<T> future;
		public WrappedFuture(final CompletableFuture<T> future) {
			this.future = future;
		}
		@Override
		public final T get() throws InterruptedException, ExecutionException {
			return future.get();
		}
		@Override
		public final T get(final long timeout) throws InterruptedException, TimeoutException, ExecutionException {
			return future.get(timeout, TimeUnit.MILLISECONDS);
		}
		@Override
		public final boolean isCompleted() {
			return future.isDone();
		}
		@Override
		public final AsyncResult<T> onResult(final ResultListener<T> listener) {
			future.thenAccept(res->listener.completed(res));
			return this;
		}
	}
	public static final <T>CompletableFuture<T> toCompletableFuture(final AsyncResult<T> result){
		if (!(result instanceof WrappedFuture))
			throw new RuntimeException("Async result was created outside of the parallel framework scope.");
		return ((WrappedFuture<T>)result).future;
	}
	public static final ExecutorService wrapExecutorImmutable(final ExecutorService executorService){
		return new ExecutorService() {
			private final ExecutorService service = executorService;
			@Override
			public final void shutdown() {
				throw new RuntimeException("This executor service is not managed by this scope.");
			}
			@Override
			public final List<Runnable> shutdownNow() {
				throw new RuntimeException("This executor service is not managed by this scope.");
			}
			@Override
			public final boolean isShutdown() {
				return service.isShutdown();
			}
			@Override
			public final boolean isTerminated() {
				return service.isTerminated();
			}
			@Override
			public final boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
				throw new RuntimeException("This executor service is not managed by this scope.");
			}
			@Override
			public final <T> Future<T> submit(final Callable<T> task) {
				return service.submit(task);
			}
			@Override
			public final <T> Future<T> submit(final Runnable task, final T result) {
				return service.submit(task, result);
			}
			@Override
			public final Future<?> submit(final Runnable task) {
				return service.submit(task);
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
				return service.invokeAll(tasks);
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks,
					final long timeout, final TimeUnit unit) throws InterruptedException {
				return service.invokeAll(tasks, timeout, unit);
			}
			@Override
			public <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
				return service.invokeAny(tasks);
			}
			@Override
			public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks,
					final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				return service.invokeAny(tasks, timeout, unit);
			}
			@Override
			public final void execute(final Runnable command) {
				service.execute(command);
			}
		};
	}
	public static final ScheduledExecutorService wrapScheduledExecutorImmutable(final ScheduledExecutorService executorService){
		return new ScheduledExecutorService() {
			private final ScheduledExecutorService service = executorService;
			@Override
			public final void shutdown() {
				throw new RuntimeException("This executor service is not managed by this scope.");
			}
			@Override
			public final List<Runnable> shutdownNow() {
				throw new RuntimeException("This executor service is not managed by this scope.");
			}
			@Override
			public final boolean isShutdown() {
				return service.isShutdown();
			}
			@Override
			public final boolean isTerminated() {
				return service.isTerminated();
			}
			@Override
			public final boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
				throw new RuntimeException("This executor service is not managed by this scope.");
			}
			@Override
			public final <T> Future<T> submit(final Callable<T> task) {
				return service.submit(task);
			}
			@Override
			public final <T> Future<T> submit(final Runnable task, final T result) {
				return service.submit(task, result);
			}
			@Override
			public final Future<?> submit(final Runnable task) {
				return service.submit(task);
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
				return service.invokeAll(tasks);
			}
			@Override
			public final <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks,
					final long timeout, final TimeUnit unit) throws InterruptedException {
				return service.invokeAll(tasks, timeout, unit);
			}
			@Override
			public <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
				return service.invokeAny(tasks);
			}
			@Override
			public final <T> T invokeAny(final Collection<? extends Callable<T>> tasks,
					final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				return service.invokeAny(tasks, timeout, unit);
			}
			@Override
			public final void execute(final Runnable command) {
				service.execute(command);
			}
			@Override
			public final ScheduledFuture<?> schedule(final Runnable command, final long delay, final TimeUnit unit) {
				return service.schedule(command, delay, unit);
			}
			@Override
			public final <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long delay, final TimeUnit unit) {
				return service.schedule(callable, delay, unit);
			}
			@Override
			public final ScheduledFuture<?> scheduleAtFixedRate(final Runnable command, final long initialDelay, final long period,
					final TimeUnit unit) {
				return service.scheduleAtFixedRate(command, initialDelay, period, unit);
			}
			@Override
			public final ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command, final long initialDelay, final long delay,
					final TimeUnit unit) {
				return service.scheduleWithFixedDelay(command, initialDelay, delay, unit);
			}
		};
	}
}
