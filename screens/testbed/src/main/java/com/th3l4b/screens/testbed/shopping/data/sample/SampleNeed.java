package com.th3l4b.screens.testbed.shopping.data.sample;

import java.util.Locale;

import com.th3l4b.screens.testbed.shopping.data.INeed;

public class SampleNeed extends SampleDataSupport implements INeed {

	private String _item;
	private long _quantity;

	public SampleNeed() {
	}

	@Override
	public void setIdentifier(String identifier) throws Exception {
		super.setIdentifier(identifier);
		_item = identifier;
	}

	@Override
	public String getLabel(Locale locale) throws Exception {
		return "USE ITEM'S LABEL";
	}

	@Override
	public void setLabel(String label, Locale locale) throws Exception {
	}

	@Override
	public String getItem() throws Exception {
		return _item;
	}

	@Override
	public void setItem(String item) throws Exception {
		_item = item;
	}

	@Override
	public long getQuantity() throws Exception {
		return _quantity;
	}

	@Override
	public void setQuantity(long quantity) throws Exception {
		_quantity = quantity;
	}

}
