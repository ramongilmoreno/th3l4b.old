package com.th3l4b.screens.base.modifications;

import com.th3l4b.screens.base.ITreeOfScreens;

public class ModificationsEngine {
	public static void apply(Iterable<Modification> modifications,
			ITreeOfScreens tree) throws Exception {
		for (Modification m : modifications) {
			if (m instanceof Modification.Added) {
				Modification.Added added = (Modification.Added) m;
				tree.addScreen(added.getScreen(), added.getParent());
			} else if (m instanceof Modification.Removed) {
				tree.removeScreen(m.getScreen());
			} else if (m instanceof Modification.RootSet) {
				tree.setRoot(m.getScreen());
			} else if (m instanceof Modification.PropertySet) {
				Modification.PropertySet property = (Modification.PropertySet) m;
				tree.setProperty(m.getScreen(), property.getProperty(),
						property.getValue());
			} else if (m instanceof Modification.PropertyRemoved) {
				Modification.PropertyRemoved property = (Modification.PropertyRemoved) m;
				tree.removeProperty(m.getScreen(), property.getProperty());
			} else {
				throw new IllegalArgumentException("Unknown modification:  "
						+ m);
			}
		}
	}
}
