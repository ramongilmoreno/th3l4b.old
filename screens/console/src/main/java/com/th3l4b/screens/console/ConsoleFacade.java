package com.th3l4b.screens.console;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.tree.TreeUtils;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.IScreensContants;
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
		PrintWriter iout = IndentedWriter.get(out);

		// Indent context
		IConsoleContext target = new DefaultConsoleContext();
		ConsoleContextUtils.copy(context, target);
		target.setWriter(iout);
		context = target;
		out.println("Entering console...");

		do {
			out.println("Screen:");
			IConsoleRenderer renderer = getDefaultConsoleRenderer();
			renderer.render(source, context);
			out.println("Actions:");

			HashMap<String, IScreen> screens = new HashMap<String, IScreen>();
			ArrayList<IScreen> list = new ArrayList<IScreen>();
			for (IScreen s : TreeUtils.bfs(context.getTree())) {
				screens.put(s.getName(), s);
				String type = s.getProperties().get(IScreensContants.TYPE);
				if (NullSafe.equals(type, IScreensContants.TYPE_FIELD)) {
					iout.println("Set field " + list.size() + " - "
							+ renderer.getLabel(s, context));
					list.add(s);
				} else if (NullSafe.equals(type,
						IScreensContants.TYPE_INTERACTION)) {
					IInteractionListener i = context.getInteractions().get(s);
					if (i != null) {

						iout.println("Do action " + list.size() + " - "
								+ renderer.getLabel(s, context));
						list.add(s);
					}

				}
			}

			out.println("Enter command do, set or quit:");
			out.print("> ");
			out.flush();
			String[] command = input.nextCommand();
			if (command == null) {
				break;
			} else if (NullSafe.equals(command[0], "do")) {
				if (command.length < 2) {
					out.println("Missing action argument. Usage 'do <action>'");
				} else {
					try {
						int index = Integer.parseInt(command[1]);
						if ((index < 0) || (index > (list.size() - 1))) {
							out.println("Index of action out of bounds.");
						} else {
							IScreen found = list.get(index);
							if (NullSafe.equals(
									found.getProperties().get(
											IScreensContants.TYPE),
									IScreensContants.TYPE_INTERACTION)) {
								context.getInteractions().get(found)
										.handleInteraction(found, context);
							} else {
								out.println("Index of is not an action.");
							}
						}
					} catch (Exception e) {
						out.println("Could not run command.");
						e.printStackTrace(out);
					}
				}
			} else if (NullSafe.equals(command[0], "set")) {
				if (command.length < 3) {
					out.println("Missing field and value argument. Usage 'set <field> <value>'");
				} else {
					try {
						int index = Integer.parseInt(command[1]);
						if ((index < 0) || (index > (list.size() - 1))) {
							out.println("Index of field out of bounds.");
						} else {
							IScreen found = list.get(index);
							if (NullSafe.equals(
									found.getProperties().get(
											IScreensContants.TYPE),
									IScreensContants.TYPE_FIELD)) {

								found.getProperties().put(
										IScreensContants.VALUE, command[2]);
								IInteractionListener i = context
										.getInteractions().get(found);
								if (i != null) {
									i.handleInteraction(found, context);
								}
							} else {
								out.println("Index is not a field.");
							}
						}
					} catch (Exception e) {
						out.println("Could not run command.");
						e.printStackTrace(out);
					}
				}
			} else if (NullSafe.equals(command[0], "quit")) {
				break;
			} else {
				out.println("Unknown command. Try again.");
			}
		} while (true);
		out.println("Quitting console...");
	}
}