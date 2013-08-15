package com.th3l4b.screens.base.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.predicate.IPredicate;
import com.th3l4b.common.data.predicate.PredicateUtils;
import com.th3l4b.screens.base.ITreeOfScreens;

public class DefaultTreeOfScreens implements ITreeOfScreens {

	private String _root = UUID.randomUUID().toString();
	private Map<String, String> _parents = new HashMap<String, String>();
	private Map<String, Map<String, String>> _properties = new HashMap<String, Map<String, String>>();

	@Override
	public String getRoot() throws Exception {
		return _root;
	}

	@Override
	public void setRoot(String root) throws Exception {
		_root = root;
	}

	@Override
	public Iterable<String> screens() throws Exception {
		return _parents.keySet();
	}

	@Override
	public Iterable<String> children(final String screen) throws Exception {
		return PredicateUtils.filter(screens(), new IPredicate<String>() {
			@Override
			public boolean accept(String t) throws Exception {
				return NullSafe.equals(_parents.get(t), screen);
			}
		});
	}

	@Override
	public String parent(String screen) throws Exception {
		return _parents.get(screen);
	}

	@Override
	public void addScreen(String screen, String parent) throws Exception {
		_parents.put(screen, parent);
	}

	@Override
	public void removeScreen(String screen) throws Exception {
		_parents.remove(screen);
	}

	@Override
	public void setProperty(String screen, String property, String value)
			throws Exception {
		propertiesToModify(screen).put(property, value);
	}

	protected Map<String, String> propertiesToModify(String screen)
			throws Exception {
		Map<String, String> found = _properties.get(screen);
		if (found == null) {
			found = new HashMap<String, String>();
			_properties.put(screen, found);
		}
		return found;
	}

	protected Map<String, String> propertiesNotToModify(String screen)
			throws Exception {
		Map<String, String> found = _properties.get(screen);
		if (found == null) {
			found = Collections.emptyMap();
		}
		return found;
	}

	@Override
	public String getProperty(String screen, String property) throws Exception {
		return propertiesNotToModify(screen).get(property);
	}

	@Override
	public void removeProperty(String screen, String property) throws Exception {
		propertiesToModify(screen).remove(property);

	}

	@Override
	public boolean hasProperty(String screen, String property) throws Exception {
		return propertiesNotToModify(screen).containsKey(property);
	}

	@Override
	public Iterable<String> properties(String screen) throws Exception {
		return propertiesNotToModify(screen).keySet();
	}

}
