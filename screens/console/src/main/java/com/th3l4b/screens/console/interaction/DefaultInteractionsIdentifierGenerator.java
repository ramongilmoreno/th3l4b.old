package com.th3l4b.screens.console.interaction;

import com.th3l4b.screens.base.IScreenItem;

/**
 * Simply returns an increasing number.
 */
public class DefaultInteractionsIdentifierGenerator implements
		IInteractionIdentifierGenerator {

	private int _value = 0;

	@Override
	public String getIdentifier(IScreenItem source, String details)
			throws Exception {
		return Integer.toString(++_value);
	}

}
