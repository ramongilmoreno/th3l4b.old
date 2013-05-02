package com.th3l4b.srm.codegen.java.basic.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.srm.codegen.java.basic.JavaNames;

public class JavaNamesTest {
	@Test
	public void test() {
		String[] tests = { "a b c", "ABC", "Foo bar", "FooBar",
				"\u00e1\u00e9\u00ed \u00f3\u00fa", "AeiOu", "Espa\u00f1a",
				"Espana", "Espa \u00f1a", "EspaNa" };
		JavaNames javaNames = new JavaNames();
		for (int i = 0; i < tests.length; i += 2) {
			Assert.assertEquals(tests[i + 1],
					javaNames.javaIdentifier(tests[i]));
		}
	}
}
