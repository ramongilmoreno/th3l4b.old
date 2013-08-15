package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.PropertiesUtils;
import com.th3l4b.screens.console.IConsoleInteractionContext;

public class SimpleConsoleRenderer implements IConsoleRenderer {

	public String getLabel(String item, IConsoleInteractionContext context)
			throws Exception {
		return PropertiesUtils.getValue(IScreensContants.LABEL, item,
				context.getLocale(), item, context.getTree());
	}

	@Override
	public boolean render(String item, IConsoleInteractionContext context)
			throws Exception {
		context.getWriter().println(getLabel(item, context));
		return false;
	}

}
