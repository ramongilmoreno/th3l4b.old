package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.PropertiesUtils;
import com.th3l4b.screens.console.IConsoleContext;

public class SimpleConsoleRenderer implements IConsoleRenderer {

	public String getLabel(IScreen item, IConsoleContext context)
			throws Exception {
		return PropertiesUtils.getValue(IScreensContants.LABEL, item.getName(),
				context.getLocale(), item);
	}

	@Override
	public boolean render(IScreen item, IConsoleContext context)
			throws Exception {
		context.getWriter().println(getLabel(item, context));
		return false;
	}

}
