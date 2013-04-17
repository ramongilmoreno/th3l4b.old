package com.th3l4b.types.runtime.basicset;

import java.text.ParseException;

import com.th3l4b.types.runtime.IJavaRuntimeType;

public abstract class AbstractType<T> implements IJavaRuntimeType<T> {

	private Class<T> _clazz;

	public AbstractType(Class<T> clazz) {
		_clazz = clazz;
	}

	public abstract String toStringNotNull(T value) throws ParseException;

	public abstract T fromStringNotNull(String value) throws ParseException;

	@Override
	public String toString(T value) throws ParseException {
		if (value == null) {
			return "";
		} else {
			return toStringNotNull(value);
		}
	}

	@Override
	public T fromString(String value) throws ParseException {
		if ((value == null) || (value.length() == 0)) {
			return null;
		} else {
			return fromStringNotNull(value);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T2> T2 fromString(String value, Class<T2> clazz)
			throws ParseException {
		if (!clazz.isAssignableFrom(_clazz)) {
			throw new IllegalArgumentException("Class is not valid. Expected: "
					+ _clazz.getName() + ", was " + clazz.getName());
		}
		return (T2) fromString(value);
	}

	@Override
	public Class<T> clazz() {
		return _clazz;
	}

}
