package com.th3l4b.screens.testbed.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.screens.base.AbstractScreensServlet;
import com.th3l4b.screens.base.IWebScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.testbed.desktop.ClipboardMenu;

@SuppressWarnings("serial")
public class DesktopScreensServlet extends AbstractScreensServlet {

	@Override
	protected IScreensConfiguration getConfiguration(
			IWebScreensClientDescriptor client) throws Exception {
		String attribute = DesktopScreensServlet.class.getName();
		IScreensConfiguration config = (IScreensConfiguration) client
				.getRequest().getSession(true).getAttribute(attribute);
		if (config == null) {
			config = ClipboardMenu.create(client);
			client.getRequest().getSession().setAttribute(attribute, config);
		}
		return config;
	}

	@Override
	protected Locale getLocale(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return request.getLocale();
	}

}
