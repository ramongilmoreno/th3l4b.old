package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.console.IConsoleInteractionContext;

public class FieldConsoleRenderer extends SimpleConsoleRenderer {

	@Override
	public String getLabel(String item, IConsoleInteractionContext context)
			throws Exception {
		return "" + super.getLabel(item, context) + ": ["
				+ context.getTree().getProperty(item, IScreensContants.VALUE)
				+ "] (" + item + ")";
	}

}
