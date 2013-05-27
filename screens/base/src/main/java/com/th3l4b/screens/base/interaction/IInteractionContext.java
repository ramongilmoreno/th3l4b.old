package com.th3l4b.screens.base.interaction;

import java.util.Locale;
import java.util.Map;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.utils.ITreeOfScreens;

public interface IInteractionContext extends IPropertied {
	ITreeOfScreens getTree() throws Exception;

	void setTree(ITreeOfScreens tree) throws Exception;

	Locale getLocale() throws Exception;

	void setLocale(Locale locale) throws Exception;
	
	Map<IScreen, IInteractionListener> getInteractions () throws Exception;
	
	void setInteractions (Map<IScreen, IInteractionListener> interactions) throws Exception;
}
