package com.th3l4b.screens.console.renderer;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.PropertiesUtils;
import com.th3l4b.screens.console.IConsoleContext;

public class DefaultConsoleRenderer extends AbstractDelegatedConsoleRenderer
		implements IScreensContants {

	SimpleConsoleRenderer _simple = new SimpleConsoleRenderer();

	ButtonConsoleRenderer _button = new ButtonConsoleRenderer();

	@Override
	protected IConsoleRenderer getRenderer(IScreen item,
			IConsoleContext context) throws Exception {
		IConsoleRenderer r = null;
		String value = PropertiesUtils.getValue(TYPE, TYPE_FIELD,
				context.getLocale(), item);
		if (NullSafe.equals(value, TYPE_INTERACTION)) {
			r = _button;
		} else {
			r = _simple;
		}
		return r;
	}
}
