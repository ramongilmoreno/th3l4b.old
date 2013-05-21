package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.console.IConsoleContext;

public class FieldConsoleRenderer extends SimpleConsoleRenderer {

	@Override
	public String getLabel(IScreen item, IConsoleContext context)
			throws Exception {
		return "" + super.getLabel(item, context) + ": ["
				+ item.getProperties().get(IScreensContants.VALUE) + "] ("
				+ item.getName() + ")";
	}

}
