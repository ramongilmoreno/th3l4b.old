package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.console.IConsoleInteractionContext;

public class ButtonConsoleRenderer extends SimpleConsoleRenderer {

	@Override
	public String getLabel(String item, IConsoleInteractionContext context)
			throws Exception {
		return "[" + super.getLabel(item, context) + "]";
	}

}
