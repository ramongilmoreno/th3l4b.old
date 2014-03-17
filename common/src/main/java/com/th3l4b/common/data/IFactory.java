package com.th3l4b.common.data;

public interface IFactory<T> {
	T create() throws Exception;
}
