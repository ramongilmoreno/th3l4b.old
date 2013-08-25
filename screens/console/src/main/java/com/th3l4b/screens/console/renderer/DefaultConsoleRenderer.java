package com.th3l4b.screens.console.renderer;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class DefaultConsoleRenderer extends AbstractDelegatedConsoleRenderer
		implements IScreensContants {

	SimpleConsoleRenderer _simple = new SimpleConsoleRenderer();
	ButtonConsoleRenderer _button = new ButtonConsoleRenderer();
	FieldConsoleRenderer _field = new FieldConsoleRenderer();

	@Override
	protected IConsoleRenderer getRenderer(String item,
			IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {
		IConsoleRenderer r = null;
		String value = context.getTree().getProperty(item, TYPE);
		if (NullSafe.equals(value, TYPE_ACTION)) {
			r = _button;
		} else if (NullSafe.equals(value, TYPE_FIELD)) {
			r = _field;
		} else {
			r = _simple;
		}
		return r;
	}
}
