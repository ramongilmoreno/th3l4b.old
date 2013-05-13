package com.th3l4b.common.data.locale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LocaleUtils {

	protected static boolean append(String prefix, String value,
			List<String> values, boolean action) {
		if (action && (value != null)) {
			String v = "";
			if (values.size() > 0) {
				v = values.get(values.size() - 1);
			}
			values.add(v + prefix + value);
			return true;
		} else {
			return false;
		}
	}

	public static List<String> getCompatibleLocaleDefinitions(Locale locale) {
		ArrayList<String> r = new ArrayList<String>();
		boolean action = true;
		action = append("", locale.getLanguage(), r, action);
		action = append("_", locale.getCountry(), r, action);
		action = append(".", locale.getVariant(), r, action);
		Collections.reverse(r);
		return r;
	}
}
