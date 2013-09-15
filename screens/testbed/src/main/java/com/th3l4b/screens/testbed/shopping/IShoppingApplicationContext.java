package com.th3l4b.screens.testbed.shopping;

import java.util.Locale;

import com.th3l4b.apps.shopping.base.codegen.srm.IShoppingContext;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public interface IShoppingApplicationContext {
	IScreensConfiguration getContext () throws Exception;
	void setContext (IScreensConfiguration context) throws Exception;
	IScreensClientDescriptor getClient () throws Exception;
	void setClient (IScreensClientDescriptor client) throws Exception;
	IShoppingContext getData () throws Exception;
	void setData (IShoppingContext data) throws Exception;
	Locale getLocale () throws Exception;
	void setLocale (Locale locale) throws Exception;
}
