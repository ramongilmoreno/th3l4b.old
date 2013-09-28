package com.th3l4b.android.screens;

import android.text.Editable;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.th3l4b.types.runtime.IJavaRuntimeType;
import com.th3l4b.types.runtime.ui.AbstractUITypeEditorSupport;

public class EditTextFieldForType<T> extends AbstractUITypeEditorSupport<T> {

	private IJavaRuntimeType<T> _type;
	private EditText _editText;

	public EditTextFieldForType(String screen, IJavaRuntimeType<T> type,
			IAndroidScreensClientDescriptor client) throws Exception {
		super(screen);
		_type = type;
		_editText = new EditText(client.getActivity());
		// http://stackoverflow.com/questions/5099814/knowing-when-edit-text-is-done-being-edited
		_editText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView arg0, int arg1,
							KeyEvent arg2) {
						try {
							uiValueChanged();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						return true;
					}
				});
		client.getViewGroup().addView(_editText);
	}

	public EditText getEditText() {
		return _editText;
	}

	@Override
	public String toString(T value) throws Exception {
		return _type.toString(value);
	}

	@Override
	public T fromString(String value) throws Exception {
		return _type.fromString(value, _type.clazz());
	}

	@Override
	public void setUIValue(T value) throws Exception {
		String s = toString(value);
		_editText.setText(s != null ? s : "");
	}

	@Override
	public T getUIValue() throws Exception {
		String s = "";
		Editable e = _editText.getText();
		if (e != null) {
			s = e.toString();
		}
		return fromString(s);
	}

}
