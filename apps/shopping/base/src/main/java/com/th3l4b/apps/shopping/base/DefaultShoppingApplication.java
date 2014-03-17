package com.th3l4b.apps.shopping.base;

import java.util.Locale;

import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingContext;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public class DefaultShoppingApplication implements IShoppingApplication {

	private IScreensConfiguration _screens;
	private IShoppingContext _data;
	private Locale _locale;

	@Override
	public IScreensConfiguration getScreens() throws Exception {
		return _screens;
	}

	@Override
	public void setScreens(IScreensConfiguration screens) throws Exception {
		_screens = screens;

	}

	@Override
	public IShoppingContext getData() throws Exception {
		return _data;
	}

	@Override
	public void setData(IShoppingContext context) throws Exception {
		_data = context;
	}

	@Override
	public Locale getLocale() throws Exception {
		return _locale;
	}

	@Override
	public void setLocale(Locale locale) throws Exception {
		_locale = locale;
	}
}
