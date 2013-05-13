package com.th3l4b.common.data.locale.junit;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.common.data.locale.LocaleUtils;

public class LocaleTest {
	@Test
	public void test() {
		Locale l = new Locale("a", "B", "c");
		List<String> all = LocaleUtils.getCompatibleLocaleDefinitions(l);
		Assert.assertEquals("a_B.c", all.get(0));
		Assert.assertEquals("a_B", all.get(1));
		Assert.assertEquals("a", all.get(2));
	}
}
