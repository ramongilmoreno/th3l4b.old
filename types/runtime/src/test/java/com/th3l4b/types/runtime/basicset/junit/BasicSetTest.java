package com.th3l4b.types.runtime.basicset.junit;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.types.runtime.IJavaRuntimeType;
import com.th3l4b.types.runtime.basicset.JavaRuntimeTypesBasicSet;

public class BasicSetTest {

	class T {
		double _decimal;
		String _text;

		public T(double decimal, String text) {
			_decimal = decimal;
			_text = text;
		}
	}

	@Test
	public void test() throws ParseException {
		JavaRuntimeTypesBasicSet context = new JavaRuntimeTypesBasicSet();
		IJavaRuntimeType<Double> decimal = context.get("decimal", Double.class);
		checkDecimal(decimal, 0, "0");
		T[] tests = new T[] { new T(123, "123"), new T(12.3, "12.3"),
				new T(123456789012345.6, "123456789012345.6"),
				new T(0.123, "0.123"), new T(0.1, "0.1"),
				new T(0.001, "0.001"), new T(0.001, "0.001"),
				new T(0.00000123, "0.00000123"),
				new T(0.123456789012345, "0.123456789012345"),
				new T(0.1234567890123459, "0.123456789012346"),
				new T(0.123456789012345999999, "0.123456789012346") };
		for (T t : tests) {
			checkDecimal(decimal, t._decimal, t._text);
			checkDecimal(decimal, -t._decimal, "-" + t._text);
		}
	}

	private void checkDecimal(IJavaRuntimeType<Double> decimal, double d,
			String expected) throws ParseException {
		String string = decimal.toString(new Double(d));
		Assert.assertEquals(expected, string);
		Double reverse = decimal.fromString(string, Double.class);
		Assert.assertTrue("Not quite similar: " + reverse.doubleValue()
				+ ", from original: " + d,
				Math.abs(reverse.doubleValue() - d) < 0.000000000000005);
		Assert.assertEquals(string, decimal.toString(reverse));
	}
}
