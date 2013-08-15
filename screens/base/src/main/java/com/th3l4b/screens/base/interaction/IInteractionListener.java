package com.th3l4b.screens.base.interaction;


public interface IInteractionListener {
	void handleInteraction(String screen, IInteractionContext context)
			throws Exception;
}
