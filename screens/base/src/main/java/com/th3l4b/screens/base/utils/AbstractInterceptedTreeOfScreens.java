package com.th3l4b.screens.base.utils;

import com.th3l4b.screens.base.IScreen;

/**
 * Notified of detected changes in a tree.
 */
public abstract class AbstractInterceptedTreeOfScreens extends TreeOfScreensFilter {
	
	public AbstractInterceptedTreeOfScreens (ITreeOfScreens delegated) {
		super(delegated);
	}
	
	@Override
	public void setRoot(IScreen root) throws Exception {
		super.setRoot(root);
		itemAdded(root);
	}

	@Override
	public void addChild(IScreen child, IScreen node) throws Exception {
		super.addChild(child, node);
		itemAdded(child);
	}
	
	@Override
	public void removeChild(IScreen child) throws Exception {
		super.removeChild(child);
		itemRemoved(child);
	}
	
	@Override
	public void updated(IScreen screen) throws Exception {
		super.updated(screen);
		itemUpdated(screen);
	}

	protected abstract void itemAdded (IScreen screen) throws Exception;
	protected abstract void itemRemoved (IScreen screen) throws Exception;
	protected abstract void itemUpdated (IScreen screen) throws Exception;
}
