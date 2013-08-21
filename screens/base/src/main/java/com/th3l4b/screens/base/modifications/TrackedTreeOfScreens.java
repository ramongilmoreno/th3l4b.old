package com.th3l4b.screens.base.modifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.utils.TreeOfScreensFilter;

public class TrackedTreeOfScreens extends TreeOfScreensFilter {

	protected List<Modification> _modifications = new ArrayList<Modification>();

	public TrackedTreeOfScreens(ITreeOfScreens tree) {
		super(tree);
	}

	public Collection<Modification> getModifications() {
		return _modifications;
	}

	public void setRoot(String root) throws Exception {
		super.setRoot(root);
		getModifications().add(new Modification.SetRoot(root));
	}

	public void addScreen(String screen, String parent) throws Exception {
		super.addScreen(screen, parent);
		getModifications().add(new Modification.AddScreen(screen, parent));
	}

	public void removeScreen(String screen) throws Exception {
		super.removeScreen(screen);
		getModifications().add(new Modification.RemoveScreen(screen));
	}

	public void setProperty(String screen, String property, String value)
			throws Exception {
		super.setProperty(screen, property, value);
		getModifications().add(
				new Modification.SetProperty(screen, property, value));
	}

	public void removeProperty(String screen, String property) throws Exception {
		super.removeProperty(screen, property);
		getModifications().add(
				new Modification.RemoveProperty(screen, property));
	}
}
