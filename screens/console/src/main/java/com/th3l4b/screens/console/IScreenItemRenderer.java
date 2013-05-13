package com.th3l4b.screens.console;

import com.th3l4b.screens.base.IScreenItem;

public interface IScreenItemRenderer {
	void render (IScreenItem item, ConsoleRendererContext context) throws Exception;
}
