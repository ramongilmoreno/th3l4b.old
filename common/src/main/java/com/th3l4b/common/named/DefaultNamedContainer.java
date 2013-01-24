package com.th3l4b.common.named;

import java.util.LinkedHashMap;

public class DefaultNamedContainer<T extends INamed> implements
		INamedContainer<T> {

	private LinkedHashMap<String, T> _items = new LinkedHashMap<String, T>();

	@Override
	public Iterable<T> items() throws Exception {
		return _items.values();
	}

	@Override
	public void add(T item) throws Exception {
		_items.put(item.getName(), item);
	}

	@Override
	public T get(String name) throws Exception {
		return _items.get(name);
	}

}
