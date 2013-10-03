package com.th3l4b.common.java;

public abstract class AbstractRunnableThrowsException implements Runnable {

	protected abstract void runWithException() throws Exception;

	@Override
	public void run() {
		try {
			runWithException();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
