package com.th3l4b.types.java;

public interface IJavaRuntimeType<T> {
	String toString (T value) throws Exception;
	T fromString (String value) throws Exception;
	<T2> T2 fromString (String value, Class<T2> clazz) throws Exception;
}
