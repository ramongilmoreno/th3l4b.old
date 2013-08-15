package com.th3l4b.screens.console.renderer;

import com.th3l4b.screens.console.IConsoleInteractionContext;

public interface IConsoleRenderer {

	String getLabel(String item, IConsoleInteractionContext context) throws Exception;

	/**
	 * @return true if all children (items and child screens) were handled.
	 *         False is parent needs to render them later.
	 */
	boolean render(String item, IConsoleInteractionContext context) throws Exception;
}
