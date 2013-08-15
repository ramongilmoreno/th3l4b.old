package com.th3l4b.screens.base.utils;

import com.th3l4b.screens.base.ITreeOfScreens;

/**
 *
 */
public class TreeOfScreensFilter implements ITreeOfScreens {

	private ITreeOfScreens _delegated;

	public TreeOfScreensFilter() {
	}

	public TreeOfScreensFilter(ITreeOfScreens delegated) {
		_delegated = delegated;
	}

	public String getRoot() throws Exception {
		return _delegated.getRoot();
	}

	public void setRoot(String root) throws Exception {
		_delegated.setRoot(root);
	}

	public Iterable<String> screens() throws Exception {
		return _delegated.screens();
	}

	public Iterable<String> children(String screen) throws Exception {
		return _delegated.children(screen);
	}

	public String parent(String screen) throws Exception {
		return _delegated.parent(screen);
	}

	public void addScreen(String screen, String parent) throws Exception {
		_delegated.addScreen(screen, parent);
	}

	public void removeScreen(String screen) throws Exception {
		_delegated.removeScreen(screen);
	}

	public void setProperty(String screen, String property, String value)
			throws Exception {
		_delegated.setProperty(screen, property, value);
	}

	public String getProperty(String screen, String property) throws Exception {
		return _delegated.getProperty(screen, property);
	}

	public void removeProperty(String screen, String property) throws Exception {
		_delegated.removeProperty(screen, property);
	}

	public boolean hasProperty(String screen, String property) throws Exception {
		return _delegated.hasProperty(screen, property);
	}

	public Iterable<String> properties(String screen) throws Exception {
		return _delegated.properties(screen);
	}

}
