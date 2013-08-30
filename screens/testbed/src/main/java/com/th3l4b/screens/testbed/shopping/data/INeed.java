package com.th3l4b.screens.testbed.shopping.data;

public interface INeed extends IAbstractDataSupport {
	String getItem() throws Exception;
	void setItem(String item) throws Exception;
	long getQuantity() throws Exception;
	void setQuantity(long quantity) throws Exception;
}
