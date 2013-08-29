package com.th3l4b.screens.testbed.shopping;

import java.util.Locale;

import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.testbed.shopping.data.IShoppingData;

public class DefaultShoppingContext implements IShoppingContext {

	private IScreensConfiguration _context;
	private IScreensClientDescriptor _client;
	private IShoppingData _data;
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
	public IShoppingData getData() throws Exception {
		return _data;
	}

	@Override
	public void setData(IShoppingData data) throws Exception {
		_data = data;
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
