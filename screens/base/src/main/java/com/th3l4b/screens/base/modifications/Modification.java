package com.th3l4b.screens.base.modifications;

import java.util.Map;

import com.th3l4b.common.data.nullsafe.NullSafe;

public abstract class Modification {
	public static final String MODIFICATION_TYPE = "type";
	public static final String SCREEN_ATTRIBUTE_NAME = "screen";

	/**
	 * Use these
	 */
	public static final String[] ALL_ATTRIBUTES = { MODIFICATION_TYPE,
			SCREEN_ATTRIBUTE_NAME,
			AbstractPropertyAction.PROPERTY_ATTRIBUTE_NAME,
			AbstractPropertyAction.VALUE_ATTRIBUTE_NAME,
			Added.PARENT_ATTRIBUTE_NAME };

	private String _screen;

	public Modification(String screen) {
		setScreen(screen);
	}

	public Modification(Map<String, String> map) {
		setScreen(map.get(SCREEN_ATTRIBUTE_NAME));
	}

	public String getScreen() {
		return _screen;
	}

	public void setScreen(String screen) {
		_screen = screen;
	}

	protected static abstract class AbstractPropertyAction extends Modification {

		public static final String PROPERTY_ATTRIBUTE_NAME = "property";
		public static final String VALUE_ATTRIBUTE_NAME = "value";

		private String _property;
		private String _value;

		public AbstractPropertyAction(String screen, String property,
				String value) {
			super(screen);
			setProperty(property);
			setValue(value);
		}

		public AbstractPropertyAction(Map<String, String> map) {
			super(map);
			setProperty(map.get(PROPERTY_ATTRIBUTE_NAME));
			setValue(map.get(VALUE_ATTRIBUTE_NAME));
		}

		public String getProperty() {
			return _property;
		}

		public void setProperty(String property) {
			_property = property;
		}

		public String getValue() {
			return _value;
		}

		public void setValue(String value) {
			_value = value;
		}
	}

	public static class Added extends Modification {

		public static final String PARENT_ATTRIBUTE_NAME = "parent";

		String _parent;

		public Added(String screen, String parent) {
			super(screen);
			setParent(parent);
		}

		public Added(Map<String, String> map) {
			super(map);
			setParent(map.get(PARENT_ATTRIBUTE_NAME));
		}

		public String getParent() {
			return _parent;
		}

		public void setParent(String parent) {
			_parent = parent;
		}
	}

	public static class RootSet extends Modification {
		public RootSet(String screen) {
			super(screen);
		}

		public RootSet(Map<String, String> map) {
			super(map);
		}
	}

	public static class Removed extends Modification {
		public Removed(String screen) {
			super(screen);
		}

		public Removed(Map<String, String> map) {
			super(map);
		}
	}

	public static class PropertySet extends AbstractPropertyAction {
		public PropertySet(String screen, String property, String value) {
			super(screen, property, value);
		}

		public PropertySet(Map<String, String> map) {
			super(map);
		}
	}

	public static class PropertyRemoved extends AbstractPropertyAction {
		public PropertyRemoved(String screen, String property) {
			super(screen, property, null);
		}

		public PropertyRemoved(Map<String, String> map) {
			super(map);
		}
	}

	public static Modification fromMap(Map<String, String> map)
			throws Exception {
		String type = map.get(MODIFICATION_TYPE);
		if (NullSafe.equals(type, Added.class.getSimpleName())) {
			return new Added(map);
		} else if (NullSafe.equals(type, Removed.class.getSimpleName())) {
			return new Removed(map);
		} else if (NullSafe.equals(type, PropertySet.class.getSimpleName())) {
			return new PropertySet(map);
		} else if (NullSafe.equals(type, PropertyRemoved.class.getSimpleName())) {
			return new PropertyRemoved(map);
		} else if (NullSafe.equals(type, RootSet.class.getSimpleName())) {
			return new RootSet(map);
		} else {
			throw new IllegalArgumentException("Unknown type: " + type);
		}
	}

}
