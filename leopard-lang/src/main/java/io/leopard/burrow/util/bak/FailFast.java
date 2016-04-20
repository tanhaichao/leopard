package io.leopard.burrow.util.bak;

import io.leopard.core.exception.TimeoutRuntimeException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 快速失败.
 * 
 * @author ahai
 * 
 */
public abstract class FailFast<TYPE> implements Callable<TYPE> {

	public TYPE execute(long timeout) throws TimeoutRuntimeException {
		final ExecutorService exec = Executors.newFixedThreadPool(1);
		try {
			Future<TYPE> future = exec.submit(this);
			return future.get(timeout, TimeUnit.MILLISECONDS); // 任务处理超时时间设为 1 秒
		} catch (TimeoutException e) {
			throw new TimeoutRuntimeException(e.getMessage() + " timeout:" + timeout);
		} catch (InterruptedException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ExecutionException e) {
			Throwable e1 = e.getCause();
			if (e1 instanceof RuntimeException) {
				throw (RuntimeException) e1;
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			// 关闭线程池
			exec.shutdownNow();
			exec.shutdown();
		}
	}

}
