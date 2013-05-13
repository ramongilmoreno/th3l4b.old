package com.th3l4b.screens.console;

import com.th3l4b.screens.base.IScreen;

public interface IScreenRenderer {
	/**
	 * @return true if all children (items and child screens) were handled.
	 *         False is parent needs to render them later.
	 */
	boolean render(IScreen item, ConsoleRendererContext context)
			throws Exception;
}
