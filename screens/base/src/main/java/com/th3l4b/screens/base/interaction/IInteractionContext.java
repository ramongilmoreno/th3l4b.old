package com.th3l4b.screens.base.interaction;

import java.util.Locale;
import java.util.Map;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.screens.base.IScreen;

public interface IInteractionContext extends IPropertied {
	IScreen getScreen() throws Exception;

	void setScreen(IScreen screen) throws Exception;

	ITree<IScreen> getTree() throws Exception;

	void setTree(ITree<IScreen> tree) throws Exception;

	Locale getLocale() throws Exception;

	void setLocale(Locale locale) throws Exception;
	
	Map<IScreen, IInteractionListener> getInteractions () throws Exception;
	
	void setInteractions (Map<IScreen, IInteractionListener> interactions) throws Exception;
}
