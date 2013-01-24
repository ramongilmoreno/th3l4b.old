package com.th3l4b.common.java;

public class EnumUtils {
	public static <T extends Enum<T>> T fromString (String value, Class<T> clazz) {
		return Enum.valueOf(clazz, value);
	}
}
