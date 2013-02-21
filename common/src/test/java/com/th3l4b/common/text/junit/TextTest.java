package com.th3l4b.common.text.junit;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.common.text.TextUtils;

public class TextTest {
	@Test
	public void testUnicodeIteration() {
		{
			// Test regulars
			String abc = "ABC";
			char[] values = { 'A', 'B', 'C' };
			Iterator<Integer> i = TextUtils.unicodeIterator(abc);
			for (char v: values) {
				Assert.assertTrue(i.hasNext());
				Assert.assertEquals((int) v, i.next().intValue());
			}
			Assert.assertFalse(i.hasNext());
		}
		{
			// Test surrogate pairs.
			// http://www.fileformat.info/info/unicode/char/1d11e/index.htm
			String gclef = "\uD834\uDD1E";
			Iterator<Integer> i = TextUtils.unicodeIterator(gclef);
			Assert.assertTrue(i.hasNext());
			Assert.assertEquals(0x1D11El, (long) i.next());
			Assert.assertFalse(i.hasNext());
		}

	}
}
