package com.th3l4b.screens.console;

import com.th3l4b.screens.base.IScreen;

public interface IConsoleRenderer {
	void render (IScreen screen, ConsoleRendererContext context) throws Exception;

}
