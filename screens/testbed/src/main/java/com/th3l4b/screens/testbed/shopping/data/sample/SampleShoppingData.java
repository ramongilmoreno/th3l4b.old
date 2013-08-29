package com.th3l4b.screens.testbed.shopping.data.sample;

import com.th3l4b.screens.testbed.shopping.data.IContainer;
import com.th3l4b.screens.testbed.shopping.data.IItem;
import com.th3l4b.screens.testbed.shopping.data.INeed;
import com.th3l4b.screens.testbed.shopping.data.IShoppingData;
import com.th3l4b.screens.testbed.shopping.data.IStore;

public class SampleShoppingData implements IShoppingData {

	SampleItemsContainer _items = new SampleItemsContainer();
	SampleStoresContainer _stores = new SampleStoresContainer();
	SampleNeedsContainer _needs = new SampleNeedsContainer();

	@Override
	public IContainer<IItem> getItemsContainer() throws Exception {
		return _items;
	}

	@Override
	public IContainer<IStore> getStoresContainer() throws Exception {
		return _stores;
	}

	@Override
	public IContainer<INeed> getNeedsContainer() throws Exception {
		return _needs;
	}

}
