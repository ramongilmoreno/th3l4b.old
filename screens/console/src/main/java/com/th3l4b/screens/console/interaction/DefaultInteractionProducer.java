package com.th3l4b.screens.console.interaction;


public class DefaultInteractionProducer implements IInteractionProducer {

	private String _screen;
	private String _interaction;

	public DefaultInteractionProducer(String screen, String interaction) {
		_interaction = interaction;
	}

	@Override
	public String getInteraction() throws Exception {
		return _interaction;
	}

	@Override
	public String getScreen() throws Exception {
		return _screen;
	}

}
