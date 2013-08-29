package com.th3l4b.screens.testbed.shopping.data;

public interface IShoppingData {
	IContainer<IItem> getItemsContainer () throws Exception;
	IContainer<IStore> getStoresContainer () throws Exception;
	IContainer<INeed> getNeedsContainer () throws Exception;
}
