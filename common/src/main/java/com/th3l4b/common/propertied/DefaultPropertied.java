package com.th3l4b.common.propertied;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultPropertied implements IPropertied {

	private Map<String, Object> _attributes = new LinkedHashMap<String, Object>();
	private Map<String, String> _properties = new LinkedHashMap<String, String>();

	@Override
	public Map<String, String> getProperties() throws Exception {
		return _properties;
	}

	@Override
	public Map<String, Object> getAttributes() throws Exception {
		return _attributes;
	}

}
