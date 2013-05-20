package com.th3l4b.common.data.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.predicate.IPredicate;

public class TreeUtils {

	public static abstract class TreeIterator<T> implements Iterator<T> {
		protected ITree<T> _tree;
		protected boolean _rootProcessed = false;

		public TreeIterator(ITree<T> t) {
			_tree = t;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public static class DepthFirstIterator<T> extends TreeIterator<T> {
		protected Stack<Iterator<T>> _stack = new Stack<Iterator<T>>();

		public DepthFirstIterator(ITree<T> ITree) {
			super(ITree);
		}

		@Override
		public boolean hasNext() {
			try {
				if (!_rootProcessed) {
					return _tree.getRoot() != null;
				} else {
					if (_stack.isEmpty()) {
						return false;
					} else {
						Iterator<T> peek = _stack.peek();
						if (peek.hasNext()) {
							return true;
						} else {
							_stack.pop();
							return hasNext();
						}
					}

				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		@Override
		public T next() {
			try {
				if (!_rootProcessed) {
					T t = _tree.getRoot();
					if (t == null) {
						throw new IllegalStateException();
					} else {
						_stack.push(_tree.getChildren(t).iterator());
						_rootProcessed = true;
					}
					return t;
				} else {
					if (_stack.isEmpty()) {
						throw new IllegalStateException();
					} else {
						T t = _stack.peek().next();
						_stack.push(_tree.getChildren(t).iterator());
						return t;
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	public static class BreathFirstIterator<T> extends TreeIterator<T> {

		public ArrayList<Iterator<T>> _list = new ArrayList<Iterator<T>>();

		public BreathFirstIterator(ITree<T> t) {
			super(t);
		}

		@Override
		public boolean hasNext() {
			try {
				if (!_rootProcessed) {
					return _tree.getRoot() != null;
				} else {
					Iterator<Iterator<T>> i = _list.iterator();
					while (i.hasNext()) {
						Iterator<T> n = i.next();
						if (n.hasNext()) {
							return true;
						} else {
							i.remove();
						}
					}

					// None found.
					return false;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		@Override
		public T next() {
			try {
				if (!_rootProcessed) {
					T t = _tree.getRoot();
					if (t == null) {
						throw new IllegalStateException();
					} else {
						_list.add(_tree.getChildren(t).iterator());
						_rootProcessed = true;
					}
					return t;
				} else {
					if (_list.isEmpty()) {
						throw new IllegalStateException();
					} else {
						T t = _list.get(0).next();
						_list.add(_tree.getChildren(t).iterator());
						return t;
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

	}

	public static <T> Iterable<T> dfs(final ITree<T> t) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new DepthFirstIterator<T>(t);
			}
		};
	}

	public static <T> Iterable<T> bfs(final ITree<T> t) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new BreathFirstIterator<T>(t);
			}
		};
	}

	public static <T> T find(ITree<T> ITree, IPredicate<T> predicate)
			throws Exception {
		for (T t : bfs(ITree)) {
			if (predicate.accept(t)) {
				return t;
			}
		}
		return null;
	}

	public static <T> ITree<T> subtree(ITree<T> ITree, T node) throws Exception {
		DefaultTree<T> r = new DefaultTree<T>();
		r.setRoot(node);
		fill(r, node, ITree);
		return r;
	}

	protected static <T> void fill(ITree<T> ITree, T node, ITree<T> source)
			throws Exception {
		for (T child : source.getChildren(node)) {
			ITree.addChild(child, node);
			fill(ITree, child, source);
		}
	}

	/**
	 * Compares two trees. Order of the children is not important.
	 * 
	 * @throws Exception
	 */
	public static <T> boolean equal(ITree<T> a, ITree<T> b) throws Exception {
		return equal(a, a.getRoot(), b, b.getRoot());
	}

	protected static <T> boolean equal(ITree<T> a, T ra, ITree<T> b, T rb)
			throws Exception {
		// Check roots are the same.
		if (!NullSafe.equals(ra, rb)) {
			return false;
		}

		// Check children are the same.
		ArrayList<T> ca = new ArrayList<T>();
		for (T t : a.getChildren(ra)) {
			ca.add(t);
		}
		ArrayList<T> cb = new ArrayList<T>();
		for (T t : b.getChildren(rb)) {
			cb.add(t);
		}

		// First number of children are the same.
		if (ca.size() != cb.size()) {
			return false;
		}

		// Two passes: first all children on a are found in b, and
		// viceversa.
		for (T t : ca) {
			if (!cb.contains(t)) {
				return false;
			}
		}

		for (T t : cb) {
			if (!ca.contains(t)) {
				return false;
			}
		}

		// If ok, proceed with subtree comparison.
		for (T t : ca) {
			if (!equal(a, t, b, t)) {
				return false;
			}
		}

		// No difference found.
		return true;
	}

}
