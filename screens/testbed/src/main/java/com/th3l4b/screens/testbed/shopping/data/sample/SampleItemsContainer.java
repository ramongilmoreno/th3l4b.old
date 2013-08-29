package com.th3l4b.screens.testbed.shopping.data.sample;

import com.th3l4b.screens.testbed.shopping.data.IItem;

public class SampleItemsContainer extends AbstractSampleContainer<IItem> {

	public SampleItemsContainer() {
		super("Item");
	}

	@Override
	protected IItem createImpl(int i) throws Exception {
		return new SampleItem();
	}

}
