package com.th3l4b.screens.console.defaultimplementation;

import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.PropertiesUtils;
import com.th3l4b.screens.console.ConsoleRendererContext;
import com.th3l4b.screens.console.IScreenRenderer;

public class DefaultScreenRenderer implements IScreenRenderer, IScreensContants {

	@Override
	public boolean render(IScreen item, ConsoleRendererContext context)
			throws Exception {
		String text = PropertiesUtils.getValue(LABEL, item.getName(),
				context.getLocale(), item);
		context.getWriter().println(text);
		return false;
	}

}
