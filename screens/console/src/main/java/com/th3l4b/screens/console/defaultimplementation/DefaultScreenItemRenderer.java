package com.th3l4b.screens.console.defaultimplementation;

import com.th3l4b.screens.base.IScreenItem;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.PropertiesUtils;
import com.th3l4b.screens.console.ConsoleRendererContext;
import com.th3l4b.screens.console.IScreenItemRenderer;

public class DefaultScreenItemRenderer implements IScreenItemRenderer,
		IScreensContants {

	@Override
	public void render(IScreenItem item, ConsoleRendererContext context)
			throws Exception {
		String text = PropertiesUtils.getValue(LABEL, item.getName(),
				context.getLocale(), item);
		context.getWriter().println(text);

	}

}