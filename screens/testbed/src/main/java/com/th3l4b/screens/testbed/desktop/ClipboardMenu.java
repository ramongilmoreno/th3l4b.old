package com.th3l4b.screens.testbed.desktop;

import java.util.LinkedHashMap;
import java.util.Random;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.interaction.IInteractionContext;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.DefaultScreensConfiguration;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.base.utils.PropertiesUtils;

public class ClipboardMenu implements IScreensContants {

	public static IScreensConfiguration create() throws Exception {
		return new ClipboardMenu().createImpl();
	}

	protected String name(String name) {
		return ClipboardMenu.class.getName() + "." + name;
	}

	protected IScreensConfiguration createImpl() throws Exception {
		DefaultTreeOfScreens r = new DefaultTreeOfScreens();
		String screen = name("Clipboard");
		{
			String spanish = "es";
			r.setProperty(screen, LABEL, "Clipboard");
			r.setProperty(screen,
					PropertiesUtils.getLocalizedProperty(LABEL, spanish),
					"Portapapeles");
		}
		r.setRoot(screen);
		final String clipboardFieldName = name("ClipboardField");
		LinkedHashMap<String, IInteractionListener> interactions = new LinkedHashMap<String, IInteractionListener>();
		{
			String action = name("Random key");
			r.addScreen(action, screen);
			r.setProperty(action, TYPE, TYPE_ACTION);
			String spanish = "es";
			r.setProperty(action, LABEL, "Random key");
			r.setProperty(action,
					PropertiesUtils.getLocalizedProperty(LABEL, spanish),
					"Clave aleatoria");
			r.setProperty(action, INTERACTION, "true");
			r.setProperty(action, INTERACTION_JAVA, action);
			interactions.put(action, new IInteractionListener() {
				@Override
				public void handleInteraction(String screen,
						IInteractionContext context) throws Exception {
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
			String spanish = "es";
			r.setProperty(clipboardFieldName, LABEL, "Clipboard field");
			r.setProperty(clipboardFieldName,
					PropertiesUtils.getLocalizedProperty(LABEL, spanish),
					"Portapapeles");
			r.setProperty(clipboardFieldName, INTERACTION, "true");
			r.setProperty(clipboardFieldName, INTERACTION_JAVA, clipboardFieldName);
			interactions.put(clipboardFieldName, new IInteractionListener() {
				@Override
				public void handleInteraction(String screen,
						IInteractionContext context) throws Exception {
					System.out.println("Copied to clipboard: "
							+ context.getTree().getProperty(screen, VALUE));
				}

			});
		}

		return new DefaultScreensConfiguration(r, interactions);
	}
}
