package com.th3l4b.types.runtime.ui;

import java.util.ArrayList;
import java.util.List;

import com.th3l4b.common.data.nullsafe.NullSafe;

/**
 * Utility class to build UI fields capable of editing types.
 */
public abstract class AbstractUITypeEditorSupport<T> {

	protected String _screen;
	protected String _value = "";
	protected boolean _enabled = true;
	protected List<IEditorListener> _listeners = new ArrayList<IEditorListener>();

	public AbstractUITypeEditorSupport(String screen) {
		resetOrDispose(screen);
	}
	
	public String getScreen() {
		return _screen;
	}

	public void resetOrDispose(String screen) {
		_screen = screen;
		_value = "";
		_enabled = true;
		_listeners.clear();
	}

	public abstract String toString(T value) throws Exception;

	public abstract T fromString(String value) throws Exception;

	protected abstract void setUIValue(T value) throws Exception;

	protected abstract T getUIValue() throws Exception;

	public void setValue(String value) throws Exception {
		_value = value;
		setUIValue(fromString(value));
	}

	public String getValue() throws Exception {
		return _value;
	}

	public void setEnabled(boolean enabled) throws Exception {
		_enabled = enabled;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	/**
	 * UI will call this method at every change. This class will decide to
	 * {@link #notifyValueChanged()}
	 * 
	 * @throws Exception
	 */
	public void uiValueChanged() throws Exception {
		String newValue = toString(getUIValue());
		if (!NullSafe.equals(_value, newValue)) {
			_value = newValue;
			notifyValueChanged();
		}
	}

	protected void notifyValueChanged() throws Exception {
		String value = getValue();
		for (IEditorListener l : _listeners) {
			l.valueChanged(getScreen(), value);
		}
	}

	public void addEditorListener(IEditorListener listener) throws Exception {
		_listeners.add(listener);
	}

	public void removeEditorListener(IEditorListener listener) throws Exception {
		_listeners.remove(listener);
	}
}
