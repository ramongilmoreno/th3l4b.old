package com.th3l4b.common.java;

import com.th3l4b.common.text.ITextConstants;

public class EnumUtils {
	public static <T extends Enum<T>> T fromString(String value, Class<T> clazz) {
		return Enum.valueOf(clazz, value);
	}

	public static <T extends Enum<T>> String toString(T value) {
		if (value == null) {
			return ITextConstants.EMPTY_STRING;
		} else {
			return value.name();
		}
	}
}
