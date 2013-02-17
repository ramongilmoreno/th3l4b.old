package com.th3l4b.common.named;

public interface INamedContainer<T extends INamed> {
	Iterable<T> items () throws Exception;
	void add (T item) throws Exception;
	T get (String name) throws Exception;
	T contains (String name) throws Exception;
	void remove (T item) throws Exception;
}
