package com.th3l4b.screens.console.interaction;

import com.th3l4b.screens.base.IScreenItem;

public interface IInteractionIdentifierGenerator {
	String getIdentifier(IScreenItem source, String details)
			throws Exception;
}
