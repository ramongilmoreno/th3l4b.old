package com.th3l4b.types.base.basicset.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.types.base.basicset.BasicSetTypesEnum;

public class BasicSetTest {
	@Test
	public void test () {
		Assert.assertEquals("boolean", BasicSetTypesEnum._boolean.getName());
	}
}
