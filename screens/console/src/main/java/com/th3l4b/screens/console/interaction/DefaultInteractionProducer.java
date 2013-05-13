package com.th3l4b.screens.console.interaction;

public class DefaultInteractionProducer implements IInteractionProducer {

	private String _interaction;

	public DefaultInteractionProducer(String interaction) {
		_interaction = interaction;
	}

	@Override
	public String getInteraction() throws Exception {
		return _interaction;
	}

}
