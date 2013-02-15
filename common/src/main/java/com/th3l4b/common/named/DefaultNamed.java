package com.th3l4b.common.named;

import com.th3l4b.common.propertied.DefaultPropertied;

public class DefaultNamed extends DefaultPropertied implements INamed {
	
	public DefaultNamed () {
	}
	
	public DefaultNamed (String name) throws Exception {
		setName(name);
	}

	@Override
	public String getName() throws Exception {
		return getProperties().get(PROPERTY_NAME);
	}
	
	@Override
	public void setName (String name) throws Exception {
		getProperties().put(PROPERTY_NAME, name);
	}

}
