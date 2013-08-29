package com.th3l4b.screens.testbed.shopping.data;

public interface IContainer<T extends IAbstractDataSupport> {
	T get (String id) throws Exception;
	Iterable<T> all() throws Exception;
	Iterable<T> search(String text) throws Exception;
	T create() throws Exception;
	void remove(T t) throws Exception;
	void restore(T t) throws Exception;
}
