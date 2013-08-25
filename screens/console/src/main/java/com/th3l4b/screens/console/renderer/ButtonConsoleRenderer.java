package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class ButtonConsoleRenderer extends SimpleConsoleRenderer {

	@Override
	public String getLabel(
			String item,
			IScreensConfiguration<? extends IConsoleScreensClientDescriptor> context)
			throws Exception {
		return "[" + super.getLabel(item, context) + "]";
	}

}
