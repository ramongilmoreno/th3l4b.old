package com.th3l4b.screens.base.interaction;

import com.th3l4b.screens.base.IScreenItem;
import com.th3l4b.screens.base.IScreen;

public interface IInteractionContext {
	Iterable<IScreenItem> getSources () throws Exception;
	void setSources (Iterable<IScreenItem> sources) throws Exception;
	IScreen getScreen () throws Exception;
	void setScreen (IScreen screen) throws Exception;
}
