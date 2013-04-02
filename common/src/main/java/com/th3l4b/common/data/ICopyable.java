package com.th3l4b.common.data;

public interface ICopyable<T> {
	public <A extends T> void copyTo(A to) throws Exception;
}
