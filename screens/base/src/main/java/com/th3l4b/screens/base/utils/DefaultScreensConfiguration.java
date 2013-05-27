package com.th3l4b.screens.base.utils;

import java.io.Serializable;
import java.util.Map;

import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.interaction.IInteractionListener;

@SuppressWarnings("serial")
public class DefaultScreensConfiguration implements IScreensConfiguration,
		Serializable {

	ITreeOfScreens _tree;

	Map<IScreen, IInteractionListener> _interactions;

	public DefaultScreensConfiguration(ITreeOfScreens tree,

	Map<IScreen, IInteractionListener> interactions) {
		_tree = tree;
		_interactions = interactions;

	}

	@Override
	public ITreeOfScreens getTree() throws Exception {
		return _tree;
	}

	@Override
	public void setTree(ITreeOfScreens tree) throws Exception {
		_tree = tree;

	}

	@Override
	public Map<IScreen, IInteractionListener> getInteractions()
			throws Exception {
		return _interactions;
	}

	@Override
	public void setInteractions(Map<IScreen, IInteractionListener> interactions)
			throws Exception {
		_interactions = interactions;

	}

}
