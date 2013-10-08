package com.th3l4b.android.helloworld;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TextFieldAndButton extends Activity {

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
		text.setText("Hello World!");
		_main.addView(text);

		final EditText field = new EditText(this);
		field.setText("Hello World!");
		// http://stackoverflow.com/questions/5099814/knowing-when-edit-text-is-done-being-edited
		field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				TextView text = new TextView(TextFieldAndButton.this);
				text.setText(Integer.toString(_count++)
						+ "Editor action - input: " + arg0.getText());
				_main.addView(text);
				return true;
			}
		});
		field.setOnFocusChangeListener(new TextView.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				TextView text = new TextView(TextFieldAndButton.this);
				text.setText(Integer.toString(_count++)
						+ "Focus change - input: "
						+ ((TextView) arg0).getText());
				_main.addView(text);
			}
		});

		/*
		 * field.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence arg0, int arg1, int
		 * arg2, int arg3) { TextView text = new
		 * TextView(TextFieldAndButton.this);
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
				TextView text = new TextView(TextFieldAndButton.this);
				text.setText(Integer.toString(_count++) + " Click: "
						+ new Date());
				_main.addView(text);
			}
		});
		_main.addView(button);

		// Add a table at the end of the main view.
		TableLayout table = new TableLayout(this);
		table.setLayoutParams(new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT,
				TableLayout.LayoutParams.MATCH_PARENT));
		TableRow.LayoutParams col1Layout = new TableRow.LayoutParams(
				0,
				TableRow.LayoutParams.WRAP_CONTENT);
		col1Layout.weight = 1;
		TableRow.LayoutParams col2Layout = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		{
			TableRow row = new TableRow(this);
			{
				TextView text1 = new TextView(this);
				text1.setText("First short");
				text1.setLayoutParams(col1Layout);
				row.addView(text1);
			}
			{
				TextView text1 = new TextView(this);
				text1.setText("First");
				text1.setLayoutParams(col2Layout);
				row.addView(text1);
			}
			table.addView(row);
		}
		{
			TableRow row = new TableRow(this);
			{
				TextView text1 = new TextView(this);
				text1.setText("Second");
				text1.setLayoutParams(col1Layout);
				row.addView(text1);
			}
			{
				TextView text1 = new TextView(this);
				text1.setText("Second short");
				row.addView(text1);
			}
			table.addView(row);
		}
		_main.addView(table);

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
