package com.th3l4b.screens.testbed.desktop;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import com.th3l4b.common.data.Pair;
import com.th3l4b.common.data.tree.DefaultTree;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.screens.base.DefaultScreen;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.interaction.IInteractionContext;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.PropertiesUtils;

public class ClipboardMenu implements IScreensContants {

	protected String name(String name) {
		return ClipboardMenu.class.getName() + "." + name;
	}

	public Pair<ITree<IScreen>, Map<IScreen, IInteractionListener>> create()
			throws Exception {

		DefaultTree<IScreen> r = new DefaultTree<IScreen>();
		DefaultScreen screen = new DefaultScreen();
		screen.setName(name("Clipboard"));
		{
			Map<String, String> properties = screen.getProperties();
			String spanish = "es";
			properties.put(LABEL, "Clipboard");
			properties.put(
					PropertiesUtils.getLocalizedProperty(LABEL, spanish),
					"Portapapeles");
		}
		r.setRoot(screen);
		LinkedHashMap<IScreen, IInteractionListener> interactions = new LinkedHashMap<IScreen, IInteractionListener>();
		{
			final DefaultScreen action = new DefaultScreen();
			action.setName(name("Random key"));
			Map<String, String> properties = action.getProperties();
			String spanish = "es";
			properties.put(LABEL, "Random key");
			properties.put(
					PropertiesUtils.getLocalizedProperty(LABEL, spanish),
					"Clave aleatoria");
			interactions.put(action, new IInteractionListener() {
				@Override
				public void handleInteraction(IInteractionContext context)
						throws Exception {
					String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"$%&/()=?*[]^{},;.:-_<>";
					StringBuffer sb = new StringBuffer();
					Random random = new Random();
					for (int i = 0; i < 8; i++) {
						int r = random.nextInt(alphabet.length());
						sb.append(alphabet.charAt(r));
					}

					DesktopSession.get(context).putIntoClipboard(sb.toString());
				}

			});
			r.addChild(action, screen);
		}

		return new Pair<ITree<IScreen>, Map<IScreen, IInteractionListener>>(r,
				interactions);
	}
}
