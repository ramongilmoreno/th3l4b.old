package com.th3l4b.common.data.tree;

public abstract class AbstractUnmodifiableTree<T> implements ITree<T> {

	@Override
	public void setRoot(T root) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild(T child, T node) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild(T child) throws Exception {
		throw new UnsupportedOperationException();
	}
}
