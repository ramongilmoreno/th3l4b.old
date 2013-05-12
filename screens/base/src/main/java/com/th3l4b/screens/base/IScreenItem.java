package com.th3l4b.screens.base;

import com.th3l4b.common.named.INamed;

public interface IScreenItem extends INamed {
	String getValue() throws Exception;
	void setValue(String value) throws Exception;
}
