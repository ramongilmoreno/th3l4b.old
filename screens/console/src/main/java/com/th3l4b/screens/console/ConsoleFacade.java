package com.th3l4b.screens.console;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.tree.TreeUtils;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.base.IScreensConstants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.AsTree;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
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

	public void handle(String source, ICommandsInput input,
			IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {

		PrintWriter out = client.getWriter();
		PrintWriter iout = IndentedWriter.get(out);

		out.println("Entering console...");

		ITreeOfScreens tree = context.getTree();

		do {
			out.println("Screen:");
			IConsoleRenderer renderer = getDefaultConsoleRenderer();
			renderer.render(source, context, client);
			out.println("Actions:");

			ArrayList<String> list = new ArrayList<String>();
			for (String s : TreeUtils.dfs(AsTree.getTree(tree))) {
				String type = tree.getProperty(s, IScreensConstants.TYPE);
				String label = renderer.getLabel(s, context, client);
				if (NullSafe.equals(type, IScreensConstants.TYPE_FIELD)) {
					iout.println("Set field " + list.size() + " - " + label);
					list.add(s);
				} else if (NullSafe.equals(type, IScreensConstants.TYPE_ACTION)) {
					IInteractionListener i = context.getInteractions().get(s);
					if (i != null) {

						iout.println("Do action " + list.size() + " - " + label);
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
							String found = list.get(index);
							if (NullSafe.equals(tree.getProperty(found,
									IScreensConstants.TYPE),
									IScreensConstants.TYPE_ACTION)) {
								// Locate the Java implementation
								String javaInteraction = tree.getProperty(
										found,
										IScreensConstants.INTERACTION_JAVA);
								if (javaInteraction == null) {
									out.println("Could not find the Java interaction for screen: "
											+ found);
								} else {
									context.getInteractions()
											.get(javaInteraction)
											.handleInteraction(found, context,
													client);
								}
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
							String found = list.get(index);
							if (NullSafe.equals(tree.getProperty(found,
									IScreensConstants.TYPE),
									IScreensConstants.TYPE_FIELD)) {
								tree.setProperty(found, IScreensConstants.VALUE,
										command[2]);
								IInteractionListener i = context
										.getInteractions().get(found);
								if (i != null) {
									i.handleInteraction(found, context, client);
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
