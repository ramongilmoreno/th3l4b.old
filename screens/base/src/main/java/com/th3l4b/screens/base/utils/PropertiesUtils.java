package com.th3l4b.screens.base.utils;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.th3l4b.common.data.locale.LocaleUtils;
import com.th3l4b.common.propertied.IPropertied;

public class PropertiesUtils {
	
	public static String getValue(String property, String defaultValue,
			Locale locale, IPropertied propertied) throws Exception {
		return getValue(property, defaultValue,
				LocaleUtils.getCompatibleLocaleDefinitions(locale), propertied);
	}

	public static String getValue(String property, String defaultValue,
			List<String> compatibleLocaleDefinitions, IPropertied propertied)
			throws Exception {
		Map<String, String> p = propertied.getProperties();

		// Find properties in the compatibility list
		for (String s : compatibleLocaleDefinitions) {
			String candidateProperty = property + "." + s;
			if (p.containsKey(candidateProperty)) {
				return p.get(candidateProperty);
			}
		}
		if (p.containsKey(property)) {
			return p.get(property);
		}

		return defaultValue;
	}
}
