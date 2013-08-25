package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class SimpleConsoleRenderer implements IConsoleRenderer {

	public String getLabel(
			String item,
			IScreensConfiguration<? extends IConsoleScreensClientDescriptor> context)
			throws Exception {
		String r = context.getTree().getProperty(item, IScreensContants.LABEL);
		if (r == null) {
			r = item;
		}
		return r;
	}

	@Override
	public boolean render(
			String item,
			IScreensConfiguration<? extends IConsoleScreensClientDescriptor> context)
			throws Exception {
		context.getClient().getWriter().println(getLabel(item, context));
		return false;
	}

}
