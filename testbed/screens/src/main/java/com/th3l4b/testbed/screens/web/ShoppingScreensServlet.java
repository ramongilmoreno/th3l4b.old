package com.th3l4b.testbed.screens.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.screens.base.AbstractScreensServlet;
import com.th3l4b.screens.base.IWebScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

@SuppressWarnings("serial")
public class ShoppingScreensServlet extends AbstractScreensServlet {

	@Override
	protected IScreensConfiguration getConfiguration(
			IWebScreensClientDescriptor client) throws Exception {
		return ShoppingServletUtils.getScreensConfiguration(client);
	}

	@Override
	protected Locale getLocale(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return request.getLocale();
	}

}
