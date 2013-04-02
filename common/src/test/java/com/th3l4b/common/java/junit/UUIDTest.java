package com.th3l4b.common.java.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.common.java.UUIDUtils;

public class UUIDTest {

	@Test
	public void test() {
		byte[] src = UUIDUtils.get();
		String asString = UUIDUtils.toString(src);
		byte[] dest = UUIDUtils.fromString(asString);
		Assert.assertArrayEquals(src, dest);
	}
}
