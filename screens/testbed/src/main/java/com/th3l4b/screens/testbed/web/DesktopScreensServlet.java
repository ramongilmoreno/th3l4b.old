package com.th3l4b.screens.testbed.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.screens.base.AbstractScreensServlet;
import com.th3l4b.screens.base.DefaultWebScreensClientDescriptor;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.IWebScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.testbed.desktop.ClipboardMenu;

@SuppressWarnings("serial")
public class DesktopScreensServlet extends AbstractScreensServlet {

	@Override
	protected IScreensConfiguration<? extends IWebScreensClientDescriptor> getConfiguration(
			HttpServletRequest request) throws Exception {
		String attribute = DesktopScreensServlet.class.getName();
		@SuppressWarnings("unchecked")
		IScreensConfiguration<? extends IWebScreensClientDescriptor> config = (IScreensConfiguration<? extends IWebScreensClientDescriptor>) request
				.getSession(true).getAttribute(attribute);
		if (config == null) {
			IWebScreensClientDescriptor client = new DefaultWebScreensClientDescriptor();
			ArrayList<Locale> locales = new ArrayList<Locale>();
			Enumeration<Locale> e = request.getLocales();
			while (e.hasMoreElements()) {
				locales.add(e.nextElement());
			}
			client.setLocales(locales);
			ArrayList<String> languages = new ArrayList<String>();
			languages.add(IScreensContants.INTERACTION_JAVA);
			languages.add(IScreensContants.INTERACTION_JAVASCRIPT);
			client.setLanguages(languages);

			config = ClipboardMenu.create(client);
			request.getSession().setAttribute(attribute, config);
		}
		return config;
	}

	@Override
	protected Locale getLocale(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return request.getLocale();
	}

}
