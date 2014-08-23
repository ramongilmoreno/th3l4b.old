package com.th3l4b.srm.runtime;

public interface IPerEntityContext<T> {
	T get (Class<?> clazz) throws Exception;
	void put (Class<?> clazz, T t) throws Exception;
	Iterable<T> all () throws Exception;
}
