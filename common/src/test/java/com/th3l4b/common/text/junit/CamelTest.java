package com.th3l4b.common.text.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.common.text.CamelUtils;

public class CamelTest {
	@Test
	public void test() {
		String[] tests = { "a b c", "ABC", "Foo bar", "FooBar", " 1 a b ",
				"1AB" };
		for (int i = 0; i < tests.length; i += 2) {
			Assert.assertEquals(tests[i + 1], CamelUtils.toCamelCase(tests[i]));
		}
	}
}
