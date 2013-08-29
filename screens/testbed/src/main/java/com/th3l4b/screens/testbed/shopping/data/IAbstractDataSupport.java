package com.th3l4b.screens.testbed.shopping.data;

import java.util.Locale;

public interface IAbstractDataSupport {
	String getIdentifier () throws Exception;
	void setIdentifier (String identifier) throws Exception;
	String getLabel (Locale locale) throws Exception;
	void setLabel (String label, Locale locale) throws Exception;
}
