package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class SimpleConsoleRenderer implements IConsoleRenderer {

	public String getLabel(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {
		String r = context.getTree().getProperty(item, IScreensContants.LABEL);
		if (r == null) {
			r = item;
		}
		return r;
	}

	@Override
	public boolean render(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {
		client.getWriter().println(getLabel(item, context, client));
		return false;
	}

}
