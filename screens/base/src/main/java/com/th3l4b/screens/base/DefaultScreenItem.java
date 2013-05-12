package com.th3l4b.screens.base;

import com.th3l4b.common.named.DefaultNamed;

public class DefaultScreenItem extends DefaultNamed implements IScreenItem, IScreensContants {
	@Override
	public String getValue() throws Exception {
		return getProperties().get(VALUE);
	}

	@Override
	public void setValue(String value) throws Exception {
		getProperties().put(VALUE, value);
	}
}
