package com.th3l4b.android.screens;

import android.app.Activity;
import android.view.ViewGroup;

import com.th3l4b.screens.base.utils.DefaultScreensClientDescriptor;

public class DefaultAndroidScreensClientDescriptor extends
		DefaultScreensClientDescriptor implements
		IAndroidScreensClientDescriptor {

	private ViewGroup _viewGroup;
	private Activity _activity;
	
	@Override
	public ViewGroup getViewGroup() throws Exception {
		return _viewGroup;
	}

	@Override
	public void setView(ViewGroup viewGroup) throws Exception {
		_viewGroup = viewGroup;
	}

	@Override
	public Activity getActivity() throws Exception {
		return _activity;
	}

	@Override
	public void setActivity(Activity activity) throws Exception {
		_activity = activity;
	}

}
