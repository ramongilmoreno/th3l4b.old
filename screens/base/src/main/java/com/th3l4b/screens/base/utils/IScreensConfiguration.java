package com.th3l4b.screens.base.utils;

import java.util.Map;

import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.interaction.IInteractionListener;

public interface IScreensConfiguration {
	ITreeOfScreens getTree() throws Exception;

	void setTree(ITreeOfScreens tree) throws Exception;

	Map<String, IInteractionListener> getInteractions() throws Exception;

	void setInteractions(Map<String, IInteractionListener> interactions)
			throws Exception;
}
