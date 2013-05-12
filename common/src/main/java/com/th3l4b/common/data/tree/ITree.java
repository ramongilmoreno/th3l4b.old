package com.th3l4b.common.data.tree;

public interface ITree<T> {
	T getRoot () throws Exception;
	void setRoot (T root) throws Exception;
	Iterable<T> getChildren(T node) throws Exception;
	void addChild (T child, T node) throws Exception;
	void removeChild (T child) throws Exception;
}
