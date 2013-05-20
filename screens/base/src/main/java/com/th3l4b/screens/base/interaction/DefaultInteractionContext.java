package com.th3l4b.screens.base.interaction;

import java.util.Locale;
import java.util.Map;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.propertied.DefaultPropertied;
import com.th3l4b.screens.base.IScreen;

public class DefaultInteractionContext extends DefaultPropertied implements
		IInteractionContext {

	protected IScreen _screen;
	protected Locale _locale;
	protected ITree<IScreen> _tree;
	protected Map<IScreen, IInteractionListener> _interactions;

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public IScreen getScreen() {
		return _screen;
	}

	public void setScreen(IScreen screen) {
		_screen = screen;
	}

	public ITree<IScreen> getTree() {
		return _tree;
	}

	public void setTree(ITree<IScreen> tree) {
		_tree = tree;
	}

	public Map<IScreen, IInteractionListener> getInteractions() {
		return _interactions;
	}
	
	public void setInteractions(Map<IScreen, IInteractionListener> interactions) {
		_interactions = interactions;
	}
}
