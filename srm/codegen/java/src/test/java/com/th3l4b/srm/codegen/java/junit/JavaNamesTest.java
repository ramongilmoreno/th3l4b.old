package com.th3l4b.srm.codegen.java.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.srm.codegen.java.JavaNames;

public class JavaNamesTest {
	@Test
	public void test() {
		String[] tests = { "a b c", "ABC", "Foo bar", "FooBar",
				"\u00e1\u00e9\u00ed \u00f3\u00fa", "AeiOu", "Espa\u00f1a",
				"Espana", "Espa \u00f1a", "EspaNa" };
		for (int i = 0; i < tests.length; i += 2) {
			Assert.assertEquals(tests[i + 1],
					JavaNames.javaIdentifier(tests[i]));
		}
	}
}
