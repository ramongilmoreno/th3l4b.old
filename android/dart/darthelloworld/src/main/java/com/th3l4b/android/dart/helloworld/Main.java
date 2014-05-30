package com.th3l4b.android.dart.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Main extends Activity {

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

		WebView web = new WebView(this);
	web.getSettings().setLoadsImagesAutomatically(true);
      web.getSettings().setJavaScriptEnabled(true);
	web.loadUrl("file:///android_asset/helloworld.html");

		_main.addView(web);

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
