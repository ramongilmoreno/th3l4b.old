package com.th3l4b.android.testbed;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Testbed extends Activity {

	private int _count;
	private LinearLayout _main;

	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// http://stackoverflow.com/questions/2868047/fullscreen-activity-in-android
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// http://stackoverflow.com/questions/11510863/android-create-edittext-on-runtime
		_main = new LinearLayout(this);
		_main.setOrientation(LinearLayout.VERTICAL);

		TextView text = new TextView(this);
		text.setText(com.th3l4b.android.screens.Main.TEST);
		_main.addView(text);

		EditText field = new EditText(this);
		field.setText("Hello Field!");
		// http://stackoverflow.com/questions/5099814/knowing-when-edit-text-is-done-being-edited
		field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				TextView text = new TextView(Testbed.this);
				text.setText(Integer.toString(_count++) + " Input: "
						+ arg0.getText());
				_main.addView(text);
				return true;
			}
		});
		/*
		 * field.addTextChangedListener(new TextWatcher() {
		 *
		 * @Override public void onTextChanged(CharSequence arg0, int arg1, int
		 * arg2, int arg3) { TextView text = new
		 * TextView(Testbed.this);
		 * text.setText(Integer.toString(_count++) + " Input: " + arg0);
		 * _main.addView(text); }
		 *
		 * @Override public void beforeTextChanged(CharSequence arg0, int arg1,
		 * int arg2, int arg3) { }
		 *
		 * @Override public void afterTextChanged(Editable arg0) { } });
		 */
		_main.addView(field);

		Button button = new Button(this);
		button.setText("Hello Button!");
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TextView text = new TextView(Testbed.this);
				text.setText(Integer.toString(_count++) + " Click: "
						+ new Date());
				_main.addView(text);
			}
		});
		_main.addView(button);

		// http://stackoverflow.com/questions/4055537/android-linearlayout-scroll
		// http://developer.android.com/reference/android/view/ViewGroup.LayoutParams.html
		ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new ScrollView.LayoutParams(
				ScrollView.LayoutParams.MATCH_PARENT,
				ScrollView.LayoutParams.MATCH_PARENT));
		scroll.addView(_main);
		setContentView(scroll);
	}

}
