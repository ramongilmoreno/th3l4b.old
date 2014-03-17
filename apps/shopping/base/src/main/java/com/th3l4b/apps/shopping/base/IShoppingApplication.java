package com.th3l4b.apps.shopping.base;

import java.util.Locale;

import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingContext;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public interface IShoppingApplication {
	IScreensConfiguration getScreens () throws Exception;
	void setScreens (IScreensConfiguration context) throws Exception;
	IShoppingContext getData () throws Exception;
	void setData (IShoppingContext data) throws Exception;
	Locale getLocale () throws Exception;
	void setLocale (Locale locale) throws Exception;
}
