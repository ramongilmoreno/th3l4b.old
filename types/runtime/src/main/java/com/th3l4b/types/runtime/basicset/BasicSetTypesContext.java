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
				long l = 0;
				for (int i = 1; i <= 15; i++) {
					l *= 10;
					l += 9;
				}
				_max = (double) l;
				for (int i = 1; i <= 15; i++) {
					l += 9;
					l /= 10;
				}
				_min = (double) l; 
			}
			
			@Override
			public String toStringNotNull(Double value) throws ParseException {
				Double v = value.doubleValue();
				if (v.isNaN()) {
					throw new IllegalArgumentException("Value is NaN: " + value);
				} else if (v > _max) {
					throw new IllegalArgumentException("Value greater than fifteen nines: " + value);
				} else if (v < _min) {
					throw new IllegalArgumentException("Value smaller than fifteen nines in decimal: " + value);
				} else {
					String s = null;
					// TODO: implement this
					return "";
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
