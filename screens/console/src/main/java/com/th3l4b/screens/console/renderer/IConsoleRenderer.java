package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public interface IConsoleRenderer {

	String getLabel(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception;

	/**
	 * @return true if all children (items and child screens) were handled.
	 *         False is parent needs to render them later.
	 */
	boolean render(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception;
}
