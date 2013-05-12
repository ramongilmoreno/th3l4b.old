package com.th3l4b.screens.base;

public class DefaultField extends DefaultScreenItem implements IField,
		IScreensContants {

	@Override
	public String getType() throws Exception {
		return getProperties().get(TYPE);
	}

	@Override
	public void setType(String type) throws Exception {
		getProperties().put(TYPE, type);
	}

}
