package com.th3l4b.types.runtime;

import java.text.ParseException;

public interface IJavaRuntimeType<T> {
	String toString (T value) throws ParseException;
	T fromString (String value) throws ParseException;
	<T2> T2 fromString (String value, Class<T2> clazz) throws ParseException;
	Class<T> clazz ();
}
