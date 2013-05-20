package com.th3l4b.screens.console.interaction;

import com.th3l4b.screens.base.IScreen;

/**
 * Simply returns an increasing number.
 */
public class DefaultInteractionsIdentifierGenerator implements
		IInteractionIdentifierGenerator {

	private int _value = 0;

	@Override
	public String getIdentifier(IScreen source, String details)
			throws Exception {
		return Integer.toString(++_value);
	}

}
