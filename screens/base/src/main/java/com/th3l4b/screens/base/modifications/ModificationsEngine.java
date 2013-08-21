package com.th3l4b.screens.base.modifications;

import com.th3l4b.screens.base.ITreeOfScreens;

public class ModificationsEngine {
	public static void apply(Iterable<Modification> modifications,
			ITreeOfScreens tree) throws Exception {
		for (Modification m : modifications) {
			if (m instanceof Modification.AddScreen) {
				Modification.AddScreen added = (Modification.AddScreen) m;
				tree.addScreen(added.getScreen(), added.getParent());
			} else if (m instanceof Modification.RemoveScreen) {
				tree.removeScreen(m.getScreen());
			} else if (m instanceof Modification.SetRoot) {
				tree.setRoot(m.getScreen());
			} else if (m instanceof Modification.SetProperty) {
				Modification.SetProperty property = (Modification.SetProperty) m;
				tree.setProperty(m.getScreen(), property.getProperty(),
						property.getValue());
			} else if (m instanceof Modification.RemoveProperty) {
				Modification.RemoveProperty property = (Modification.RemoveProperty) m;
				tree.removeProperty(m.getScreen(), property.getProperty());
			} else {
				throw new IllegalArgumentException("Unknown modification:  "
						+ m);
			}
		}
	}
}
