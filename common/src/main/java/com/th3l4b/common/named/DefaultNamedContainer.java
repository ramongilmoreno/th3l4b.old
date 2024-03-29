package com.th3l4b.common.named;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.IndentedWriter;
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
		String name = item.getName();
		if (_items.containsKey(name)) {
			throwExceptionWhenNameExists(item);
		}

		_items.put(item.getName(), item);
	}

	protected void throwExceptionWhenNameExists(T item) throws Exception {
		throw new IllegalArgumentException("Duplicated name: " + item  + " on container: " + this);
	}

	@Override
	public T get(String name) throws Exception {
		if (!_items.containsKey(name)) {
			throwExceptionWhenNameDoesNotExists(name);
		}
		return _items.get(name);
	}

	protected void throwExceptionWhenNameDoesNotExists(String name) throws Exception {
		throw new IllegalArgumentException("Unexisting name: " + name + " on container: " + this);
	}

	@Override
	public T contains(String name) throws Exception {
		return _items.get(name);
	}

	@Override
	public void remove(T item) throws Exception {
		_items.remove(item);
	}

	@Override
	public void print(PrintWriter out) {
		int i = 0;
		PrintWriter iout = IndentedWriter.get(out);
		for (Entry<String, T> e : _items.entrySet()) {
			out.println("#" + (i++) + " key: " + e.getKey() + ", value:");
			TextUtils.print(e.getValue(), iout);
		}
		iout.flush();
	}
	
	@Override
	public void clear() throws Exception {
		_items.clear();
	}
}
