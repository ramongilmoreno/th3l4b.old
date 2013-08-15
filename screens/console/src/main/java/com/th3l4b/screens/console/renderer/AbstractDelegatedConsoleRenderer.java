package com.th3l4b.screens.console.renderer;

import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.console.ConsoleContextUtils;
import com.th3l4b.screens.console.DefaultConsoleContext;
import com.th3l4b.screens.console.IConsoleInteractionContext;

public abstract class AbstractDelegatedConsoleRenderer implements
		IConsoleRenderer {

	protected abstract IConsoleRenderer getRenderer(String item,
			IConsoleInteractionContext context) throws Exception;

	@Override
	public String getLabel(String item, IConsoleInteractionContext context)
			throws Exception {
		return getRenderer(item, context).getLabel(item, context);
	}

	@Override
	public boolean render(String item, IConsoleInteractionContext context)
			throws Exception {

		// Render item itself.
		IConsoleRenderer renderer = getRenderer(item, context);
		boolean done = renderer.render(item, context);

		// Render children if not rendered
		if (!done) {
			IConsoleInteractionContext childContext = new DefaultConsoleContext();
			ConsoleContextUtils.copy(context, childContext);
			childContext
					.setWriter(IndentedWriter.get(childContext.getWriter()));

			for (String child : context.getTree().children(item)) {
				render(child, childContext);
			}
		}
		return true;
	}

}
