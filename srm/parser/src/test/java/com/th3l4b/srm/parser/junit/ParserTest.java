package com.th3l4b.srm.parser.junit;

import java.io.InputStream;

import org.junit.Test;

import com.th3l4b.srm.parser.ParserUtils;

public class ParserTest {
	@Test
	public void testParser() throws Exception {

		InputStream is = getClass().getResourceAsStream("Test.srm");
		try {
			ParserUtils.parse(is);
		} finally {
			is.close();
		}
	}
}
