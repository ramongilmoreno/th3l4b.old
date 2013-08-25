package com.th3l4b.screens.testbed.desktop;

import com.th3l4b.common.propertied.DefaultPropertied;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public class DesktopSession extends DefaultPropertied {

	public static DesktopSession get(IScreensConfiguration context)
			throws Exception {
		String key = DesktopSession.class.getName();
		Object r = context.getAttributes().get(key);
		if (r == null) {
			r = new DesktopSession();
			context.getAttributes().put(key, r);
		}
		return (DesktopSession) r;
	}

	public void putIntoClipboard(String value) {
		System.out.println("Put into clipboard: " + value);
	}
}
