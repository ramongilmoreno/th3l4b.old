package com.th3l4b.screens.base.utils;

import java.util.Map;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.interaction.IInteractionListener;

public interface IScreensConfiguration {
	ITree<IScreen> getTree () throws Exception;
	void setTree (ITree<IScreen> tree) throws Exception;
	Map<IScreen, IInteractionListener> getInteractions () throws Exception;
	void setInteractions (Map<IScreen, IInteractionListener> interactions) throws Exception;
}
