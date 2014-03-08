package com.th3l4b.srm.codegen.base.names.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.srm.codegen.base.names.CamelUtils;

public class CamelTest {
	@Test
	public void test() {
		String[] tests = { "a b c", "ABC", "Foo bar", "FooBar" };
		for (int i = 0; i < tests.length; i += 2) {
			Assert.assertEquals(tests[i + 1], CamelUtils.toCamelCase(tests[i]));
		}
	}
}
