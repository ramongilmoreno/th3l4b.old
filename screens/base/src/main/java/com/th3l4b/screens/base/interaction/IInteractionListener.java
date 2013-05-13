package com.th3l4b.screens.base.interaction;

import com.th3l4b.screens.base.IScreenItem;


public interface IInteractionListener {
	void handleInteractions (Iterable<IScreenItem> interactions, IInteractionContext context) throws Exception;
}
