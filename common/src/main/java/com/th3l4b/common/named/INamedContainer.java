package com.th3l4b.common.named;

public interface INamedContainer<T extends INamed> {
	Iterable<T> items () throws Exception;
	T get (String name) throws Exception;
}
