package com.th3l4b.screens.base.interaction;

import java.util.Locale;
import java.util.Map;

import com.th3l4b.common.propertied.DefaultPropertied;
import com.th3l4b.screens.base.ITreeOfScreens;

public class DefaultInteractionContext extends DefaultPropertied implements
		IInteractionContext {

	protected Locale _locale;
	protected ITreeOfScreens _tree;
	protected Map<String, IInteractionListener> _interactions;

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public ITreeOfScreens getTree() {
		return _tree;
	}

	public void setTree(ITreeOfScreens tree) {
		_tree = tree;
	}

	public Map<String, IInteractionListener> getInteractions() {
		return _interactions;
	}

	public void setInteractions(Map<String, IInteractionListener> interactions) {
		_interactions = interactions;
	}
}
