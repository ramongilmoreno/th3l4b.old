package com.th3l4b.android.testbed;

import com.th3l4b.android.screens.AbstractAndroidFacade;
import com.th3l4b.android.screens.IAndroidScreensClientDescriptor;
import com.th3l4b.apps.shopping.base.Shopping;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Testbed extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// http://stackoverflow.com/questions/2868047/fullscreen-activity-in-android
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		try {
			new AbstractAndroidFacade() {
				@Override
				protected IScreensConfiguration createScreensConfiguration(
						IAndroidScreensClientDescriptor client)
						throws Exception {
					return new Shopping().sample(client);
				}
			}.onCreate(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
