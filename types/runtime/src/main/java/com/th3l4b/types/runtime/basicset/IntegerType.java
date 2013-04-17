package com.th3l4b.types.runtime.basicset;

import java.text.ParseException;

public class IntegerType extends AbstractType<Long> {

	public IntegerType() {
		super(Long.class);
	}

	@Override
	public String toStringNotNull(Long value) throws ParseException {
		if (value == null) {
			return "";
		} else {
			return Long.toString(value);
		}
	}

	@Override
	public Long fromStringNotNull(String value) throws ParseException {
		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return Long.parseLong(value);
		}
	}

}
