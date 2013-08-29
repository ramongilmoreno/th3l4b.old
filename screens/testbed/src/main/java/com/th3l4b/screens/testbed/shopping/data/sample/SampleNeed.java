package com.th3l4b.screens.testbed.shopping.data.sample;

import java.util.Locale;

import com.th3l4b.screens.testbed.shopping.data.IItem;
import com.th3l4b.screens.testbed.shopping.data.INeed;

public class SampleNeed extends SampleDataSupport implements INeed {

	private IItem _item;
	private long _quantity;
	
	public SampleNeed () {
		_item = new SampleItem();
	}
	
	@Override
	public String getLabel(Locale locale) throws Exception {
		return _item.getLabel(locale);
	}
	
	@Override
	public void setLabel(String label, Locale locale) throws Exception {
		_item.setLabel(label, locale);
	}
	
	@Override
	public IItem getItem() throws Exception {
		return _item;
	}

	@Override
	public void setItem(IItem item) throws Exception {
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
