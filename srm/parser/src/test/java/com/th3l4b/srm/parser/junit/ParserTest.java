package com.th3l4b.srm.parser.junit;

import org.junit.Test;

import com.th3l4b.srm.parser.Main;


public class ParserTest {
	@Test
	public void testParser() throws Exception {
		Main.parse(getClass().getResourceAsStream("Test.srm"));
	}
}
