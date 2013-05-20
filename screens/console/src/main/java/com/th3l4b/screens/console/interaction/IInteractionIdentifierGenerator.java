package com.th3l4b.screens.console.interaction;

import com.th3l4b.screens.base.IScreen;

public interface IInteractionIdentifierGenerator {
	String getIdentifier(IScreen source, String details)
			throws Exception;
}
