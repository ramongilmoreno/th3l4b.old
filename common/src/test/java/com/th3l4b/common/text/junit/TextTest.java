package com.th3l4b.common.text.junit;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.common.text.TextUtils;

public class TextTest {

	public static String GCLEF = "\uD834\uDD1E";

	@Test
	public void testUnicodeIteration() {
		{
			// Test regulars
			String abc = "ABC";
			char[] values = { 'A', 'B', 'C' };
			Iterator<Integer> i = TextUtils.unicodeIterator(abc);
			for (char v : values) {
				Assert.assertTrue(i.hasNext());
				Assert.assertEquals((int) v, i.next().intValue());
			}
			Assert.assertFalse(i.hasNext());
		}
		{
			// Test surrogate pairs.
			// http://www.fileformat.info/info/unicode/char/1d11e/index.htm
			Iterator<Integer> i = TextUtils.unicodeIterator(GCLEF);
			Assert.assertTrue(i.hasNext());
			Assert.assertEquals(0x1D11El, (long) i.next());
			Assert.assertFalse(i.hasNext());
		}

	}

	@Test
	public void testJavaEscape() throws Exception {
		String result = "\\uD834\\uDD1E".toLowerCase();
		Assert.assertEquals(result, TextUtils.escapeJavaString(GCLEF));
	}

	@Test
	public void testCIdentifier() throws Exception {
		String[] tests = { "A", "A", "a", "A", "a a", "AA", "0", "XX0", "a0",
				"A0", "a b_c", "ABC", "@1@a@b", "XX1AB" };
		for (int i = 0; i < tests.length; i += 2) {
			Assert.assertEquals(tests[i + 1], TextUtils.cIdentifier(tests[i]));
		}
	}
}
