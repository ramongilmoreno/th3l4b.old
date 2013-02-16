package com.th3l4b.common.named;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.TextUtils;

public class DefaultNamedContainer<T extends INamed> implements
		INamedContainer<T>, IPrintable {

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

	@Override
	public boolean contains(T item) throws Exception {
		return false;
	}

	@Override
	public void remove(T item) throws Exception {
		_items.remove(item);
	}

	@Override
	public void print(PrintWriter out) {
		int i = 0;
		for (Entry<String, T> e : _items.entrySet()) {
			out.println("#" + (i++) + " " + e.getKey());
			TextUtils.print(e.getValue(), out);
		}

	}
}
