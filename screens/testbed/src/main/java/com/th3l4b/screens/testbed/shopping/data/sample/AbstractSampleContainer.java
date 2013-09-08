package com.th3l4b.screens.testbed.shopping.data.sample;

import java.util.ArrayList;

import com.th3l4b.screens.testbed.shopping.data.IAbstractDataSupport;
import com.th3l4b.screens.testbed.shopping.data.IContainer;

public abstract class AbstractSampleContainer<T extends IAbstractDataSupport>
		implements IContainer<T> {

	protected String _name;

	protected abstract T createImpl(int i) throws Exception;

	protected Iterable<T> list() throws Exception {
		ArrayList<T> r = new ArrayList<T>();
		for (int i = 0; i < 7; i++) {
			r.add(create(i));
		}
		return r;
	}

	protected T create(int i) throws Exception {
		T r = createImpl(i);
		r.setIdentifier("" + i);
		r.setLabel(_name + " #" + i);
		return r;
	}

	public AbstractSampleContainer(String name) {
		_name = name;
	}

	@Override
	public Iterable<T> all() throws Exception {
		return list();
	}

	@Override
	public Iterable<T> search(String text) throws Exception {
		return list();
	}

	@Override
	public T create() throws Exception {
		return create(0);
	}

	@Override
	public void remove(T t) throws Exception {
	}

	@Override
	public void restore(T t) throws Exception {
	}

	@Override
	public T get(String id) throws Exception {
		T r = create(Integer.parseInt(id));
		return r;
	}
}
