package com.th3l4b.screens.base.utils;

import java.io.Serializable;
import java.util.Map;

import com.th3l4b.common.propertied.DefaultPropertied;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.interaction.IInteractionListener;

@SuppressWarnings("serial")
public class DefaultScreensConfiguration extends DefaultPropertied implements
		IScreensConfiguration, Serializable {

	private ITreeOfScreens _tree;

	private Map<String, IInteractionListener> _interactions;

	public DefaultScreensConfiguration(ITreeOfScreens tree,

	Map<String, IInteractionListener> interactions) {
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
	public Map<String, IInteractionListener> getInteractions() throws Exception {
		return _interactions;
	}

	@Override
	public void setInteractions(Map<String, IInteractionListener> interactions)
			throws Exception {
		_interactions = interactions;

	}
}
