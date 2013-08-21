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
			AddScreen.PARENT_ATTRIBUTE_NAME };

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

	public static class AddScreen extends Modification {

		public static final String PARENT_ATTRIBUTE_NAME = "parent";

		String _parent;

		public AddScreen(String screen, String parent) {
			super(screen);
			setParent(parent);
		}

		public AddScreen(Map<String, String> map) {
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

	public static class SetRoot extends Modification {
		public SetRoot(String screen) {
			super(screen);
		}

		public SetRoot(Map<String, String> map) {
			super(map);
		}
	}

	public static class RemoveScreen extends Modification {
		public RemoveScreen(String screen) {
			super(screen);
		}

		public RemoveScreen(Map<String, String> map) {
			super(map);
		}
	}

	public static class SetProperty extends AbstractPropertyAction {
		public SetProperty(String screen, String property, String value) {
			super(screen, property, value);
		}

		public SetProperty(Map<String, String> map) {
			super(map);
		}
	}

	public static class RemoveProperty extends AbstractPropertyAction {
		public RemoveProperty(String screen, String property) {
			super(screen, property, null);
		}

		public RemoveProperty(Map<String, String> map) {
			super(map);
		}
	}

	public static Modification fromMap(Map<String, String> map)
			throws Exception {
		String type = map.get(MODIFICATION_TYPE);
		if (NullSafe.equals(type, AddScreen.class.getSimpleName())) {
			return new AddScreen(map);
		} else if (NullSafe.equals(type, RemoveScreen.class.getSimpleName())) {
			return new RemoveScreen(map);
		} else if (NullSafe.equals(type, SetProperty.class.getSimpleName())) {
			return new SetProperty(map);
		} else if (NullSafe.equals(type, RemoveProperty.class.getSimpleName())) {
			return new RemoveProperty(map);
		} else if (NullSafe.equals(type, SetRoot.class.getSimpleName())) {
			return new SetRoot(map);
		} else {
			throw new IllegalArgumentException("Unknown modification type: " + type);
		}
	}

}
