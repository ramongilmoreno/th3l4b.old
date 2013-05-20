package com.th3l4b.screens.console;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.tree.TreeUtils;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.console.renderer.DefaultConsoleRenderer;
import com.th3l4b.screens.console.renderer.IConsoleRenderer;

public class ConsoleFacade {

	private IConsoleRenderer _defaultRenderer;

	protected IConsoleRenderer getDefaultConsoleRenderer() throws Exception {
		if (_defaultRenderer == null) {
			_defaultRenderer = new DefaultConsoleRenderer();
		}
		return _defaultRenderer;
	}

	public void handle(IScreen source, ICommandsInput input,
			IConsoleContext context) throws Exception {
		PrintWriter out = context.getWriter();
		out.println("Entering console...");
		do {
			out.println("Screen:");
			IConsoleRenderer renderer = getDefaultConsoleRenderer();
			renderer.render(source, context);

			ArrayList<IInteractionListener> listeners = new ArrayList<IInteractionListener>();
			for (IScreen s : TreeUtils.bfs(context.getTree())) {
				IInteractionListener i = context.getInteractions().get(s);
				if (i != null) {
					out.println("" + listeners.size() + " - "
							+ renderer.getLabel(s, context));
				}
				listeners.add(i);
			}

			String[] command = input.nextCommand();
			if (command == null) {
				break;
			} else if (NullSafe.equals(command[0], "do")) {
				if (command.length < 2) {
					out.println("Missing action argument. Usage 'do <action>'");
				} else {
					try {
						int index = Integer.parseInt(command[1]);
						if ((index < 0) || (index > (listeners.size() - 1))) {
							out.println("Index of action out of bounds.");
							break;
						}
						listeners.get(index).handleInteraction(context);
					} catch (Exception e) {
						out.println("Could not run command.");
						e.printStackTrace(out);
					}
				}
			}

		} while (true);
		out.println("Quitting console...");
	}
}
