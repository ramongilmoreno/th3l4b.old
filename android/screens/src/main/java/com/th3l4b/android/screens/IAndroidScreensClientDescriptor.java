package com.th3l4b.android.screens;

import android.app.Activity;
import android.view.ViewGroup;

import com.th3l4b.screens.base.utils.IScreensClientDescriptor;

public interface IAndroidScreensClientDescriptor extends IScreensClientDescriptor {
	ViewGroup getViewGroup () throws Exception;
	void setViewGroup (ViewGroup viewGroup) throws Exception;
	Activity getActivity () throws Exception;
	void setActivity (Activity activity) throws Exception;
}
