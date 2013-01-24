package com.th3l4b.srm.base;

import com.th3l4b.common.named.INamed;

public interface IField extends INamed {
	String getType () throws Exception;
	void setType (String type) throws Exception;
}
