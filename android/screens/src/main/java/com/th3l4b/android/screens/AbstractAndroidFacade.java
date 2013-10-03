package com.th3l4b.android.screens;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.th3l4b.common.java.AbstractRunnableThrowsException;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public abstract class AbstractAndroidFacade {

	LinearLayout main;

	protected abstract IScreensConfiguration createScreensConfiguration(
			IAndroidScreensClientDescriptor client) throws Exception;

	public void onCreate(Activity activity) throws Exception {
		// Prepare ui holder
		// http://stackoverflow.com/questions/11510863/android-create-edittext-on-runtime
		main = new LinearLayout(activity);
		main.setOrientation(LinearLayout.VERTICAL);

		// http://stackoverflow.com/questions/4055537/android-linearlayout-scroll
		// http://developer.android.com/reference/android/view/ViewGroup.LayoutParams.html
		ScrollView scroll = new ScrollView(activity);
		scroll.setLayoutParams(new ScrollView.LayoutParams(
				ScrollView.LayoutParams.MATCH_PARENT,
				ScrollView.LayoutParams.MATCH_PARENT));
		scroll.addView(main);
		activity.setContentView(scroll);

		// Create client
		DefaultAndroidScreensClientDescriptor client = new DefaultAndroidScreensClientDescriptor();
		client.setActivity(activity);
		client.setView(main);
		render(createScreensConfiguration(client), client);
	}

	protected void render(final IScreensConfiguration configuration,
			final IAndroidScreensClientDescriptor client) throws Exception {
		AndroidRenderView renderer = new AndroidRenderView();
		renderer.render(configuration, client,
				new AbstractRunnableThrowsException() {
					@Override
					protected void runWithException() throws Exception {
						main.removeAllViews();
						render(configuration, client);
					}
				});
	}
}
