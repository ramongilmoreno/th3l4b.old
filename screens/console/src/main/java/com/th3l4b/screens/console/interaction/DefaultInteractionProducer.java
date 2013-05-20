package com.th3l4b.screens.console.interaction;

import com.th3l4b.screens.base.IScreen;

public class DefaultInteractionProducer implements IInteractionProducer {

	private IScreen _screen;
	private String _interaction;

	public DefaultInteractionProducer(IScreen screen, String interaction) {
		_interaction = interaction;
	}

	@Override
	public String getInteraction() throws Exception {
		return _interaction;
	}

	@Override
	public IScreen getScreen() throws Exception {
		return _screen;
	}

}
