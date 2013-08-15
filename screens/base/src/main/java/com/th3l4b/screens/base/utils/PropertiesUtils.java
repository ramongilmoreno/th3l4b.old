package com.th3l4b.screens.base.utils;

import java.util.List;
import java.util.Locale;

import com.th3l4b.common.data.locale.LocaleUtils;
import com.th3l4b.screens.base.ITreeOfScreens;

public class PropertiesUtils {
	
	public static String getLocalizedProperty (String property, String localeDefinition) {
		return property + "." + localeDefinition;
	}
	
	public static String getValue(String property, String defaultValue,
			Locale locale, String screen, ITreeOfScreens tree) throws Exception {
		return getValue(property, defaultValue,
				LocaleUtils.getCompatibleLocaleDefinitions(locale), screen, tree);
	}

	public static String getValue(String property, String defaultValue,
			List<String> compatibleLocaleDefinitions, String screen, ITreeOfScreens tree)
			throws Exception {
		// Find properties in the compatibility list
		for (String s : compatibleLocaleDefinitions) {
			String candidateProperty = getLocalizedProperty(property, s);
			if (tree.hasProperty(screen, candidateProperty)) {
				return tree.getProperty(screen, candidateProperty);
			}
		}
		if (tree.hasProperty(screen, property)) {
			return tree.getProperty(screen, property);
		}

		return defaultValue;
	}
}
