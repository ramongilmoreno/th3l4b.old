package com.th3l4b.screens.testbed.desktop;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Random;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.DefaultScreensConfiguration;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public class ClipboardMenu implements IScreensContants {

	public static <T extends IScreensClientDescriptor> IScreensConfiguration<T> create(
			T client) throws Exception {
		return new ClipboardMenu().createImpl(client);
	}

	protected String name(String name) {
		return ClipboardMenu.class.getName() + "." + name;
	}

	protected <T extends IScreensClientDescriptor> IScreensConfiguration<T> createImpl(
			T client) throws Exception {
		DefaultTreeOfScreens r = new DefaultTreeOfScreens();
		boolean english = true;
		for (Locale l : client.getLocales()) {
			if (NullSafe.equals(l.getLanguage(), "es")) {
				english = false;
				break;
			} else if (NullSafe.equals(l.getLanguage(), "en")) {
				break;
			}
		}

		String screen = name("Clipboard");
		r.setProperty(screen, LABEL, english ? "Clipboard" : "Portapapeles");
		r.setRoot(screen);
		final String clipboardFieldName = name("ClipboardField");
		LinkedHashMap<String, IInteractionListener> interactions = new LinkedHashMap<String, IInteractionListener>();
		{
			String action = name("Random key");
			r.addScreen(action, screen);
			r.setProperty(action, TYPE, TYPE_ACTION);
			r.setProperty(screen, LABEL, english ? "Random key"
					: "Clave aleatoria");
			r.setProperty(action, INTERACTION, "true");
			r.setProperty(action, INTERACTION_JAVA, action);
			interactions.put(action, new IInteractionListener() {
				@Override
				public void handleInteraction(
						String screen,
						IScreensConfiguration<? extends IScreensClientDescriptor> context)
						throws Exception {
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
			r.setProperty(clipboardFieldName, TYPE, TYPE_FIELD);
			r.setProperty(screen, LABEL, english ? "Clipboard field"
					: "Portapapeles");
			r.setProperty(clipboardFieldName, INTERACTION, "true");
			r.setProperty(clipboardFieldName, INTERACTION_JAVA,
					clipboardFieldName);
			interactions.put(clipboardFieldName, new IInteractionListener() {
				@Override
				public void handleInteraction(
						String screen,
						IScreensConfiguration<? extends IScreensClientDescriptor> context)
						throws Exception {
					System.out.println("Copied to clipboard: "
							+ context.getTree().getProperty(screen, VALUE));
				}

			});
		}

		DefaultScreensConfiguration<T> config = new DefaultScreensConfiguration<T>(
				r, interactions);
		config.setTree(r);
		config.setInteractions(interactions);
		config.setClient(client);
		return config;
	}
}
