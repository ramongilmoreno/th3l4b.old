package com.th3l4b.screens.console.renderer;

import java.io.PrintWriter;

import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public abstract class AbstractDelegatedConsoleRenderer implements
		IConsoleRenderer {

	protected abstract IConsoleRenderer getRenderer(String item,
			IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception;

	@Override
	public String getLabel(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {
		return getRenderer(item, context, client).getLabel(item, context,
				client);
	}

	@Override
	public boolean render(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {

		// Render item itself.
		IConsoleRenderer renderer = getRenderer(item, context, client);
		boolean done = renderer.render(item, context, client);

		// Render children if not rendered
		if (!done) {
			PrintWriter original = client.getWriter();
			client.setWriter(IndentedWriter.get(original));
			for (String child : context.getTree().children(item)) {
				render(child, context, client);
			}
			client.setWriter(original);
		}
		return true;
	}

}
