package com.th3l4b.types.runtime.basicset;

import java.text.ParseException;
import java.util.HashMap;

import com.th3l4b.types.runtime.IJavaRuntimeType;
import com.th3l4b.types.runtime.IJavaRuntimeTypesContext;

public class BasicSetTypesContext implements IJavaRuntimeTypesContext {

	private HashMap<String, IJavaRuntimeType<?>> _types = new HashMap<String, IJavaRuntimeType<?>>();

	public BasicSetTypesContext() {
		_types.put("integer", new IntegerType());
		_types.put("decimal", new AbstractType<Double>(Double.class) {
			double _min, _max;

			{
				long l = 1;
				for (int i = 0; i < 15; i++) {
					l *= 10;
				}
				_max = (double) l;
				l = 1;
				for (int i = 0; i < 15; i++) {
					l /= 10;
				}
				_min = (double) l;
			}

			@Override
			public String toStringNotNull(Double value) throws ParseException {
				double v = value.doubleValue();
				if (value.isNaN()) {
					throw new IllegalArgumentException("Value is NaN: " + value);
				}
				boolean negative = v < 0;
				v = Math.abs(v);
				if (v > _max) {
					v = _max;
				} else if (v < _min) {
					v = _min;
				}
				double l = Math.floor(Math.log10(v));
				l = Math.abs(Math.max(-15, l - 15));
				v = v * Math.pow(10, l);
				long lon = (long) Math.round(v);
				StringBuffer sb = new StringBuffer(Long.toString(lon));
				int idx = sb.length() - ((int) l);
				if (idx < 0) {
					for (int j = idx; j < 0; j++) {
						sb.insert(0, '0');
					}
					sb.insert(0, '.');
				} else if ((idx >= 0) && (idx <= sb.length())) {
					sb.insert(idx, '.');
				}
				String s = sb.toString().replaceAll("[0]*$", "")
						.replaceFirst("\\.$", "").replaceFirst("^\\.", "0.")
						.replaceFirst("^$", "0");
				if (negative) {
					return "-" + s;
				} else {
					return s;
				}
			}

			@Override
			public Double fromStringNotNull(String value) throws ParseException {
				try {
					return Double.parseDouble(value);
				} catch (NumberFormatException nfe) {
					throw new ParseException(value, 0);
				}
			}

		});
		_types.put("boolean", new AbstractType<Boolean>(Boolean.class) {
			public static final String YES = "Y";
			public static final String NO = "N";

			@Override
			public String toStringNotNull(Boolean value) throws ParseException {
				return value.booleanValue() ? YES : NO;
			}

			@Override
			public Boolean fromStringNotNull(String value)
					throws ParseException {
				if (value.equals(YES)) {
					return Boolean.TRUE;
				} else if (value.equals(NO)) {
					return Boolean.FALSE;
				} else {
					throw new ParseException(value, 0);
				}
			}
		});
		_types.put("date", new IntegerType());
		_types.put("timestamp", new IntegerType());
		_types.put("label", new StringType(50));
		_types.put("string", new StringType(500));
		_types.put("text", new StringType(5000));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IJavaRuntimeType<T> get(String name, Class<T> clazz) {
		IJavaRuntimeType<?> r = _types.get(name);
		if (!clazz.isAssignableFrom(r.clazz())) {
			throw new IllegalArgumentException("Invalid class: " + clazz
					+ ", expected " + r.clazz());
		}
		return (IJavaRuntimeType<T>) r;
	}

}
