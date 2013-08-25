package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class FieldConsoleRenderer extends SimpleConsoleRenderer {

	@Override
	public String getLabel(
			String item,
			IScreensConfiguration<? extends IConsoleScreensClientDescriptor> context)
			throws Exception {
		return "" + super.getLabel(item, context) + ": ["
				+ context.getTree().getProperty(item, IScreensContants.VALUE)
				+ "] (" + item + ")";
	}

}
