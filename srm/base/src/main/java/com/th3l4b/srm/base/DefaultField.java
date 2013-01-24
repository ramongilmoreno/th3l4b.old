package com.th3l4b.srm.base;

import com.th3l4b.common.named.DefaultNamed;

public class DefaultField extends DefaultNamed implements IField,
		IModelConstants {

	@Override
	public String getType() throws Exception {
		return getProperties().get(PROPERTY_FIELD_TYPE);
	}

	@Override
	public void setType(String type) throws Exception {
		getProperties().put(PROPERTY_FIELD_TYPE, type);

	}

}
