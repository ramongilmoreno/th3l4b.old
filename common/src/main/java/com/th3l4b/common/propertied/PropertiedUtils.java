package com.th3l4b.common.propertied;

import java.util.Map;

import com.th3l4b.common.data.IFactory;

public class PropertiedUtils {

	public static String defaultProprty(String property,
			IPropertied propertied, IFactory<String> factory) throws Exception {
		Map<String, String> properties = propertied.getProperties();
		String r = properties.get(property);
		if (r == null) {
			r = factory.create();
			properties.put(property, r);
		}
		return r;
	}

	public static <T> T defaultAttribute(String attribute,
			IPropertied propertied, IFactory<T> factory) throws Exception {
		Map<String, Object> attributes = propertied.getAttributes();
		@SuppressWarnings("unchecked")
		T r = (T) attributes.get(attribute);
		if (r == null) {
			r = factory.create();
			attributes.put(attribute, r);
		}
		return r;
	}

}
