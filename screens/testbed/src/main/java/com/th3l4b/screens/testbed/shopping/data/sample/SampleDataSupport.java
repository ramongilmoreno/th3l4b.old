package com.th3l4b.screens.testbed.shopping.data.sample;

import java.util.Locale;

import com.th3l4b.screens.testbed.shopping.data.IAbstractDataSupport;

public class SampleDataSupport implements IAbstractDataSupport {

	private String _identifier;
	private String _label;

	@Override
	public String getIdentifier() throws Exception {
		return _identifier;
	}

	@Override
	public void setIdentifier(String identifier) throws Exception {
		_identifier = identifier;
	}

	@Override
	public String getLabel(Locale locale) throws Exception {
		return _label;
	}

	@Override
	public void setLabel(String label, Locale locale) throws Exception {
		_label = label;
	}

}
