package com.th3l4b.srm.base.normalized;

import java.util.Map;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamed;

public class DefaultNormalizedModel extends
		DefaultNamedContainer<INormalizedEntity> implements INormalizedModel {
	INamed _asNamed = new DefaultNamed();

	public Map<String, String> getProperties() throws Exception {
		return _asNamed.getProperties();
	}

	public String getName() throws Exception {
		return _asNamed.getName();
	}

	public Map<String, Object> getAttributes() throws Exception {
		return _asNamed.getAttributes();
	}

	public void setName(String name) throws Exception {
		_asNamed.setName(name);
	}
	
}
