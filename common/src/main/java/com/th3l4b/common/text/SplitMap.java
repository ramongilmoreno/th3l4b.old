package com.th3l4b.common.text;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SplitMap {

	public static final String DEFAULT_SEPARATOR = ".";

	public static String typeOfKey(String key, String separator) {
		int i = key.indexOf(separator);
		if (i == -1)
			throw new RuntimeException("No type found on key: " + key);
		return key.substring(0, i);
	}

	public static String tailOfKey(String key, String separator) {
		int i = key.indexOf(separator);
		if (i == -1)
			throw new RuntimeException("No tail found on key: " + key);
		return key.substring(i + separator.length(), key.length());
	}

	public static Map<String, Map<String, String>> split(Map<String, String> p) {
		return split(p, DEFAULT_SEPARATOR);
	}

	/**
	 * Creates a map of properties indexed by the key. The properties map stored
	 * does not contain the key.
	 */
	public static Map<String, Map<String, String>> split(Map<String, String> p,
			String separator) {
		HashMap<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		for (String key : p.keySet()) {
			String newKey = typeOfKey(key, separator);
			Map<String, String> properties = result.get(newKey);
			if (properties == null) {
				properties = new HashMap<String, String>();
				result.put(newKey, properties);
			}
			properties.put(tailOfKey(key, separator), p.get(key));
		}

		return result;
	}

	public static Map<String, Map<String, String>> split(final Properties p,
			String separator) {
		Map<String, String> map = Collections
				.unmodifiableMap(new AbstractMap<String, String>() {

					Set<Map.Entry<String, String>> _entries;

					@Override
					public Set<Map.Entry<String, String>> entrySet() {
						if (_entries == null) {
							LinkedHashMap<String, String> e = new LinkedHashMap<String, String>();
							for (Map.Entry<Object, Object> a : p.entrySet()) {
								Object key = a.getKey();
								Object value = a.getValue();

								if ((key != null) && (key instanceof String)
										&& (value != null)
										&& (value instanceof String)) {
									e.put((String) key, (String) value);
								}
							}
							_entries = e.entrySet();
						}
						return _entries;
					}
				});
		return split(map, separator);
	}
}
