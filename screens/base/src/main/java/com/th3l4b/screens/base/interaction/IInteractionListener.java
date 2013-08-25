package com.th3l4b.screens.base.interaction;

import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public interface IInteractionListener {
	void handleInteraction(String screen,
			IScreensConfiguration<? extends IScreensClientDescriptor> context)
			throws Exception;
}
