package com.th3l4b.screens.console.renderer;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.PropertiesUtils;
import com.th3l4b.screens.console.IConsoleInteractionContext;

public class DefaultConsoleRenderer extends AbstractDelegatedConsoleRenderer
		implements IScreensContants {

	SimpleConsoleRenderer _simple = new SimpleConsoleRenderer();
	ButtonConsoleRenderer _button = new ButtonConsoleRenderer();
	FieldConsoleRenderer _field = new FieldConsoleRenderer();

	@Override
	protected IConsoleRenderer getRenderer(String item,
			IConsoleInteractionContext context) throws Exception {
		IConsoleRenderer r = null;
		String value = PropertiesUtils.getValue(TYPE, null,
				context.getLocale(), item, context.getTree());
		if (NullSafe.equals(value, TYPE_INTERACTION)) {
			r = _button;
		} else if (NullSafe.equals(value, TYPE_FIELD)) {
			r = _field;
		} else {
			r = _simple;
		}
		return r;
	}
}
