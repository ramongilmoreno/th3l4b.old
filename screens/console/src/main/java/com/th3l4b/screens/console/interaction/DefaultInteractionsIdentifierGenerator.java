package com.th3l4b.screens.console.interaction;

/**
 * Simply returns an increasing number.
 */
public class DefaultInteractionsIdentifierGenerator implements
		IInteractionIdentifierGenerator {

	private int _value = 0;

	@Override
	public String getIdentifier(String source, String details)
			throws Exception {
		return Integer.toString(++_value);
	}

}
