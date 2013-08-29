package com.th3l4b.screens.testbed.shopping.data.sample;

import com.th3l4b.screens.testbed.shopping.data.IStore;

public class SampleStoresContainer extends AbstractSampleContainer<IStore> {

	public SampleStoresContainer() {
		super("Store");
	}

	@Override
	protected IStore createImpl(int i) throws Exception {
		return new SampleStore();
	}


}
