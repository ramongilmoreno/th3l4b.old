package com.th3l4b.screens.console.renderer;

import java.io.PrintWriter;

import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public abstract class AbstractDelegatedConsoleRenderer implements
		IConsoleRenderer {

	protected abstract IConsoleRenderer getRenderer(String item,
			IScreensConfiguration<? extends IConsoleScreensClientDescriptor> context) throws Exception;

	@Override
	public String getLabel(String item, IScreensConfiguration<? extends IConsoleScreensClientDescriptor> context)
			throws Exception {
		return getRenderer(item, context).getLabel(item, context);
	}

	@Override
	public boolean render(String item, IScreensConfiguration<? extends IConsoleScreensClientDescriptor> context)
			throws Exception {

		// Render item itself.
		IConsoleRenderer renderer = getRenderer(item, context);
		boolean done = renderer.render(item, context);

		// Render children if not rendered
		if (!done) {
			IConsoleScreensClientDescriptor client = context.getClient();
			PrintWriter original = client.getWriter();
			client.setWriter(IndentedWriter.get(original));
			for (String child : context.getTree().children(item)) {
				render(child, context);
			}
			client.setWriter(original);
		}
		return true;
	}

}
