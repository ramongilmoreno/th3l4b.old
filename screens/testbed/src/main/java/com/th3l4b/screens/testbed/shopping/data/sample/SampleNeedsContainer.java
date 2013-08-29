package com.th3l4b.screens.testbed.shopping.data.sample;

import com.th3l4b.screens.testbed.shopping.data.INeed;

public class SampleNeedsContainer extends AbstractSampleContainer<INeed> {

	public SampleNeedsContainer() {
		super("Need");
	}

	@Override
	protected INeed createImpl(int i) throws Exception {
		return new SampleNeed();
	}

}
