package com.th3l4b.screens.testbed.shopping.data.sample;

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
	public String getLabel() throws Exception {
		return _label;
	}

	@Override
	public void setLabel(String label) throws Exception {
		_label = label;
	}

}
