package com.th3l4b.types.runtime.basicset;

import java.text.ParseException;

public class StringType extends AbstractType<String> {

	private int _limit;

	public StringType(int limit) {
		super(String.class);
		_limit = limit;
	}

	public int getLimit() {
		return _limit;
	}

	public static String limit(String original, int limit) {
		if (original == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (int i = 0; i < original.length(); i++) {
			if (count == limit) {
				break;
			}
			char c = original.charAt(i);
			sb.append(c);
			count++;
			if (Character.isHighSurrogate(c)) {
				// Append low
				sb.append(original.charAt(++i));
			}
		}
		return sb.toString();
	}

	@Override
	public String toStringNotNull(String value) throws ParseException {
		return limit(value, getLimit());
	}

	@Override
	public String fromStringNotNull(String value) throws ParseException {
		return toString(value);
	}

}
