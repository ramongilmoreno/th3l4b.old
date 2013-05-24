package com.th3l4b.screens.base.utils;

import java.io.Serializable;
import java.util.Map;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.interaction.IInteractionListener;

@SuppressWarnings("serial")
public class DefaultScreensConfiguration implements IScreensConfiguration,
		Serializable {

	ITree<IScreen> _tree;

	Map<IScreen, IInteractionListener> _interactions;

	public DefaultScreensConfiguration(ITree<IScreen> tree,

	Map<IScreen, IInteractionListener> interactions) {
		_tree = tree;
		_interactions = interactions;

	}

	@Override
	public ITree<IScreen> getTree() throws Exception {
		return _tree;
	}

	@Override
	public void setTree(ITree<IScreen> tree) throws Exception {
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
