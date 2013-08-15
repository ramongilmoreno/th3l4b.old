package com.th3l4b.screens.console.interaction;

public interface IInteractionIdentifierGenerator {
	String getIdentifier(String source, String details)
			throws Exception;
}
