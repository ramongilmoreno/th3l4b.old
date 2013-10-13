package com.th3l4b.screens.testbed.desktop;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Random;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IScreensConstants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.DefaultScreensConfiguration;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public class ClipboardMenu implements IScreensConstants {

	public static IScreensConfiguration create(IScreensClientDescriptor client)
			throws Exception {
		return new ClipboardMenu().createImpl(client);
	}

	protected String name(String name) {
		return ClipboardMenu.class.getName() + "." + name;
	}

	protected static boolean english(IScreensClientDescriptor client)
			throws Exception {
		boolean english = true;
		for (Locale l : client.getLocales()) {
			if (NullSafe.equals(l.getLanguage(), "es")) {
				english = false;
				break;
			} else if (NullSafe.equals(l.getLanguage(), "en")) {
				break;
			}
		}
		return english;
	}

	protected IScreensConfiguration createImpl(IScreensClientDescriptor client)
			throws Exception {
		DefaultTreeOfScreens r = new DefaultTreeOfScreens();
		boolean english = english(client);

		String screen = name("Clipboard");
		r.setProperty(screen, LABEL, english ? "Clipboard" : "Portapapeles");
		r.setRoot(screen);
		final String clipboardFieldName = name("ClipboardField");
		LinkedHashMap<String, IInteractionListener> interactions = new LinkedHashMap<String, IInteractionListener>();
		{
			String action = name("Random key");
			r.addScreen(action, screen);
			r.setProperty(action, ORDER_INDEX, "1");
			r.setProperty(action, TYPE, TYPE_ACTION);
			r.setProperty(action, LABEL, english ? "Random key"
					: "Clave aleatoria");
			r.setProperty(action, INTERACTION, "true");
			r.setProperty(action, INTERACTION_JAVA, action);
			interactions.put(action, new IInteractionListener() {
				@Override
				public void handleInteraction(String screen,
						IScreensConfiguration context,
						IScreensClientDescriptor client) throws Exception {
					String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"$%&/()=?*[]^{},;.:-_<>";
					StringBuffer sb = new StringBuffer();
					Random random = new Random();
					for (int i = 0; i < 8; i++) {
						int r = random.nextInt(alphabet.length());
						sb.append(alphabet.charAt(r));
					}
					DesktopSession.get(context).putIntoClipboard(sb.toString());
					context.getTree().setProperty(clipboardFieldName, VALUE,
							sb.toString());
				}

			});
		}
		{
			r.addScreen(clipboardFieldName, screen);
			r.setProperty(clipboardFieldName, ORDER_INDEX, "2");
			r.setProperty(clipboardFieldName, TYPE, TYPE_FIELD);
			r.setProperty(clipboardFieldName, LABEL,
					english ? "Clipboard field" : "Portapapeles");
			r.setProperty(clipboardFieldName, INTERACTION, "true");
			r.setProperty(clipboardFieldName, INTERACTION_JAVA,
					clipboardFieldName);
			interactions.put(clipboardFieldName, new IInteractionListener() {
				@Override
				public void handleInteraction(String screen,
						IScreensConfiguration context,
						IScreensClientDescriptor client) throws Exception {
					System.out.println("Copied to clipboard: "
							+ context.getTree().getProperty(screen, VALUE));
				}

			});
		}
		final String items = name("Items");
		{
			r.addScreen(items, screen);
			r.setProperty(items, ORDER_INDEX, "5");
			r.setProperty(items, TYPE, TYPE_HIDDEN);
			r.setProperty(items, LABEL, english ? "Items" : "Elementos");
		}
		{
			String action = name("Add item");
			r.setProperty(action, ORDER_INDEX, "3");
			r.addScreen(action, screen);
			r.setProperty(action, TYPE, TYPE_ACTION);
			r.setProperty(action, LABEL, english ? "Add item"
					: "Nuevo elemento");
			r.setProperty(action, INTERACTION, "true");
			r.setProperty(action, INTERACTION_JAVA, action);
			interactions.put(action, new IInteractionListener() {
				@Override
				public void handleInteraction(String screen,
						IScreensConfiguration context,
						IScreensClientDescriptor client) throws Exception {
					int count = 0;
					Iterator<String> i = context.getTree().children(items)
							.iterator();
					while (i.hasNext()) {
						i.next();
						count++;
					}
					String item = name("Item #" + count);
					context.getTree().addScreen(item, items);
					context.getTree().setProperty(item, TYPE, TYPE_HIDDEN);
					context.getTree().setProperty(
							item,
							LABEL,
							(english(client) ? "Item" : "Elemento")
									+ (" #" + count));
				}

			});
		}
		{
			String action = name("Remove all items");
			r.setProperty(action, ORDER_INDEX, "4");
			r.addScreen(action, screen);
			r.setProperty(action, TYPE, TYPE_ACTION);
			r.setProperty(action, LABEL, english ? "Remove all items"
					: "Quitar todos los elementos");
			r.setProperty(action, INTERACTION, "true");
			r.setProperty(action, INTERACTION_JAVA, action);
			interactions.put(action, new IInteractionListener() {
				@Override
				public void handleInteraction(String screen,
						IScreensConfiguration context,
						IScreensClientDescriptor client) throws Exception {
					ITreeOfScreens tree = context.getTree();
					for (String c : tree.children(items)) {
						tree.removeScreen(c);
					}
				}

			});
			r.setProperty(action, INTERACTION_JAVASCRIPT,
					"function (screen, context, client) {\n"
							+ "	var children = context.tree.children('" + items
							+ "');\n" + "	for (var i in children) {\n"
							+ "		context.tree.removeScreen(children[i]);\n"
							+ "	}\n" + "}");
		}

		DefaultScreensConfiguration config = new DefaultScreensConfiguration(r,
				interactions);
		config.setTree(r);
		config.setInteractions(interactions);
		return config;
	}
}
