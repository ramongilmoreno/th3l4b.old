package com.th3l4b.screens.base.interaction;

import com.th3l4b.screens.base.IScreen;

public interface IInteractionListener {
	void handleInteraction(IScreen screen, IInteractionContext context)
			throws Exception;
}
