package com.th3l4b.screens.testbed.shopping;

import java.util.Locale;

import com.th3l4b.apps.shopping.base.codegen.srm.IShoppingContext;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public class DefaultShoppingApplicationContext implements IShoppingApplicationContext {

	private IScreensConfiguration _context;
	private IScreensClientDescriptor _client;
	private IShoppingContext _data;
	private Locale _locale;

	@Override
	public IScreensConfiguration getContext() throws Exception {
		return _context;
	}

	@Override
	public void setContext(IScreensConfiguration context) throws Exception {
		_context = context;

	}

	@Override
	public IScreensClientDescriptor getClient() throws Exception {
		return _client;
	}

	@Override
	public void setClient(IScreensClientDescriptor client) throws Exception {
		_client = client;
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
