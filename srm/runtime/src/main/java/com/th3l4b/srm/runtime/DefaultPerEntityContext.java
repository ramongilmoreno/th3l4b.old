package com.th3l4b.srm.runtime;

import java.util.LinkedHashMap;

public class DefaultPerEntityContext<T> implements IPerEntityContext<T> {

	LinkedHashMap<String, T> _items = new LinkedHashMap<String, T>();
	
	protected String key (Class<?> clazz) throws Exception {
		return clazz.getName();
	}

	@Override
	public T get(Class<?> clazz) throws Exception {
		return _items.get(key(clazz));
	}

	@Override
	public void put(Class<?> clazz, T t) throws Exception {
		_items.put(key(clazz), t);
	}

	@Override
	public Iterable<T> all() throws Exception {
		return _items.values();
	}

}
