package com.th3l4b.screens.base.utils;

import com.th3l4b.common.data.tree.DefaultTree;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.screens.base.IScreen;

public class DefaultTreeOfScreens extends DefaultNamedContainer<IScreen>
		implements ITreeOfScreens {

	DefaultTree<IScreen> _delegated = new DefaultTree<IScreen>();

	@Override
	public IScreen getRoot() throws Exception {
		return _delegated.getRoot();
	}

	@Override
	public void setRoot(IScreen root) throws Exception {
		IScreen old = getRoot();
		if (old != null) {
			super.remove(old);
		}
		super.add(root);
		_delegated.setRoot(root);
	}

	@Override
	public Iterable<IScreen> getChildren(IScreen node) throws Exception {
		return _delegated.getChildren(node);
	}

	@Override
	public void addChild(IScreen child, IScreen node) throws Exception {
		if (super.contains(child.getName()) == null) {
			super.add(child);
		}
		_delegated.addChild(child, node);

	}

	@Override
	public void removeChild(IScreen child) throws Exception {
		if (super.contains(child.getName()) == null) {
			super.remove(child);
		}
		_delegated.removeChild(child);
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void updated(IScreen screen) throws Exception {
	}

	@Override
	public void add(IScreen item) throws Exception {
		throw new UnsupportedOperationException(
				"Use tree methods instead (addChild)");
	}

	@Override
	public void remove(IScreen item) throws Exception {
		throw new UnsupportedOperationException(
				"Use tree methods instead (removeChild)");
	}
	
	@Override
	public void clear() throws Exception {
		throw new UnsupportedOperationException(
				"Use tree methods instead (removeChild)");
	}

}
