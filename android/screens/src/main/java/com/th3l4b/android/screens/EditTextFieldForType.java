package com.th3l4b.android.screens;

import android.text.Editable;
import android.text.TextWatcher;
//import android.view.KeyEvent;
import android.widget.EditText;
//import android.widget.TextView;

import com.th3l4b.common.java.AbstractRunnableThrowsException;
import com.th3l4b.types.runtime.IJavaRuntimeType;
import com.th3l4b.types.runtime.ui.AbstractUITypeEditorSupport;

public class EditTextFieldForType<T> extends AbstractUITypeEditorSupport<T> {

	private IJavaRuntimeType<T> _type;
	private EditText _editText;

	public EditTextFieldForType(String screen, IJavaRuntimeType<T> type,
			final IAndroidScreensClientDescriptor client) throws Exception {
		super(screen);
		_type = type;
		_editText = new EditText(client.getActivity());
		final Runnable runnable = new AbstractRunnableThrowsException() {
			@Override
			protected void runWithException() throws Exception {
				uiValueChanged();
			}
		};

		// http://stackoverflow.com/questions/5099814/knowing-when-edit-text-is-done-being-edited
		// http://stackoverflow.com/questions/11641670/similar-function-swingutilities-invokelater-in-android
		_editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
				runnable.run();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
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
