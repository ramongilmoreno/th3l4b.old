package com.th3l4b.screens.console.interaction;

import com.th3l4b.screens.base.IScreen;

public interface IInteractionProducer {
	IScreen getScreen () throws Exception;
	String getInteraction () throws Exception;
}
