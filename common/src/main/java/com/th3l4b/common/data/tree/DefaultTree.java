package com.th3l4b.common.data.tree;

import java.util.LinkedHashMap;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.predicate.IPredicate;
import com.th3l4b.common.data.predicate.PredicateUtils;

public class DefaultTree<T> implements ITree<T> {

	private LinkedHashMap<T, T> _parents = new LinkedHashMap<T, T>();
	private T _root;

	@Override
	public T getRoot() throws Exception {
		return _root;
	}

	@Override
	public void setRoot(T root) throws Exception {
		_root = root;

	}

	@Override
	public Iterable<T> getChildren(final T node) throws Exception {
		// Simply query the list of keys for those whose parents are the
		// argument.
		return PredicateUtils.filter(_parents.keySet(), new IPredicate<T>() {
			@Override
			public boolean accept(T t) throws Exception {
				return NullSafe.equals(node, _parents.get(t));
			}
		});
	}

	@Override
	public void addChild(T child, T node) throws Exception {
		_parents.put(child, node);
	}

	@Override
	public void removeChild(T child) throws Exception {
		_parents.remove(child);
	}

}
