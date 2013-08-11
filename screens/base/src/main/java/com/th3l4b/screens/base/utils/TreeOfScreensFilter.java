package com.th3l4b.screens.base.utils;

import com.th3l4b.screens.base.IScreen;

public class TreeOfScreensFilter implements ITreeOfScreens {
	
	private ITreeOfScreens _delegated;

	public void clear() throws Exception {
		_delegated.clear();
	}

	public TreeOfScreensFilter (ITreeOfScreens delegated) {
		_delegated = delegated;
	}
	
	public ITreeOfScreens getDelegated() {
		return _delegated;
	}
	
	public void setDelegated(ITreeOfScreens delegated) {
		_delegated = delegated;
	}
	
	public IScreen getRoot() throws Exception {
		return _delegated.getRoot();
	}

	public Iterable<IScreen> items() throws Exception {
		return _delegated.items();
	}

	public void setRoot(IScreen root) throws Exception {
		_delegated.setRoot(root);
	}

	public void add(IScreen item) throws Exception {
		_delegated.add(item);
	}

	public Iterable<IScreen> getChildren(IScreen node) throws Exception {
		return _delegated.getChildren(node);
	}

	public IScreen get(String name) throws Exception {
		return _delegated.get(name);
	}

	public void addChild(IScreen child, IScreen node) throws Exception {
		_delegated.addChild(child, node);
	}

	public IScreen contains(String name) throws Exception {
		return _delegated.contains(name);
	}

	public void removeChild(IScreen child) throws Exception {
		_delegated.removeChild(child);
	}

	public void updated(IScreen screen) throws Exception {
		_delegated.updated(screen);
	}

	public void remove(IScreen item) throws Exception {
		_delegated.remove(item);
	}

}
