package com.th3l4b.screens.base;

import com.th3l4b.common.data.tree.DefaultTree;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamedContainer;

public class DefaultScreen extends DefaultNamed implements IScreen {

	INamedContainer<IScreenItem> _fields = new DefaultNamedContainer<IScreenItem>();
	ITree<IScreen> _tree = new DefaultTree<IScreen>();

	public IScreen getRoot() throws Exception {
		return _tree.getRoot();
	}

	public void setRoot(IScreen root) throws Exception {
		_tree.setRoot(root);
	}

	public Iterable<IScreen> getChildren(IScreen node) throws Exception {
		return _tree.getChildren(node);
	}

	public void addChild(IScreen child, IScreen node) throws Exception {
		_tree.addChild(child, node);
	}

	public void removeChild(IScreen child) throws Exception {
		_tree.removeChild(child);
	}

	public Iterable<IScreenItem> items() throws Exception {
		return _fields.items();
	}

	public void add(IScreenItem item) throws Exception {
		_fields.add(item);
	}

	public IScreenItem get(String name) throws Exception {
		return _fields.get(name);
	}

	public IScreenItem contains(String name) throws Exception {
		return _fields.contains(name);
	}

	public void remove(IScreenItem item) throws Exception {
		_fields.remove(item);
	}

}
